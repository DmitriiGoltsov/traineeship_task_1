package com.goltsov.ylab.task;

import com.goltsov.ylab.task.exception.IllegalReadingChange;
import com.goltsov.ylab.task.exception.UserNotFoundException;
import com.goltsov.ylab.task.pseudocontroller.PseudoMeterController;
import com.goltsov.ylab.task.pseudocontroller.PseudoUserController;
import com.goltsov.ylab.task.security.AuthenticationManager;
import com.goltsov.ylab.task.service.ReadingServiceImpl;
import com.goltsov.ylab.task.service.UserServiceImpl;

import java.time.YearMonth;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        AuthenticationManager authenticationManager = userService.getAuthenticationManager();
        PseudoUserController userController = new PseudoUserController(userService, authenticationManager);
        ReadingServiceImpl readingService = new ReadingServiceImpl();
        PseudoMeterController meterController = new PseudoMeterController(readingService, authenticationManager);

        System.out.println("Добро пожаловать в приложение для внесения показаний приборов учёта.");

        boolean flag = true;
        Scanner scanner = new Scanner(System.in);

            while (flag) {
                try {
                    String message = """
                            Пожалуйста, выберете действие которое вы хотите совершить:
                            1 - Войти в систему (пройти аутентификацию)
                            2 - Зарегистрироваться в системе
                            3 - Передать показания (требуется аутентификация)
                            4 - Получить актуальные показания (последние переданные показания). Требуется аутентификация
                            5 - Получить показания за конкретный месяц (требуется аутентификация)
                            0 - Закрыть приложение
                            """;

                    System.out.println(message);

                    int choice = scanner.nextInt();
                    System.out.println("\nВы выбрали " + choice);
                    switch (choice) {
                        case 1 -> {
                            System.out.println("Введите ваш логин/email и нажмите ВВОД: \n");
                            String login = scanner.next();
                            System.out.println("Введите ваш пароль и нажмите ВВОД: \n");
                            String password = scanner.next();
                            authenticationManager.authenticate(login, password);

                            System.out.println("Вы успешно вошли в систему. Текущий пользователь "
                                    + authenticationManager.getCurrentUser().toString());
                        }
                        case 2 -> {
                            System.out.println("Введите ваше имя и нажмите ВВОД");
                            String name = scanner.next();
                            System.out.println("Введите электронную почту и нажмите ВВОД");
                            String email = scanner.next();
                            System.out.println("Введите желаемый пароль и нажмите ВВОД");
                            String password = scanner.next();
                            if (userController.addPayer(name, email, password)) {
                                System.out.println("Пользователь с логином " + email + " был успешно зарегистрирован.");
                            }
                        }
                        case 3 -> {
                            System.out.println("""
                                    Выберете тип счётчика из предложенных ниже и введите соответствующую цифру:
                                    0 - ХВС
                                    1 - ГВС
                                    2 - Отопление
                                    """);
                            int meterType = scanner.nextInt();
                            System.out.println("\nУкажите показания, которые вы хотите передать: \n");
                            int reading = scanner.nextInt();
                            meterController.postReading(reading, meterType);
                        }
                        case 4 -> {
                            Map<String, Map<YearMonth, Integer>> userActualReadings =
                                    meterController.getUserActualReading();

                            System.out.println("\n" + userActualReadings.toString());
                        }
                        case 5 -> {
                            System.out.println("Укажите месяц показаний в формате двух цифр");
                            int month = scanner.nextInt();
                            System.out.println("Укажите год показаний в формате четырёх цифр");
                            int year = scanner.nextInt();
                            YearMonth yearMonth = YearMonth.of(year, month);

                            System.out.println("За период " + yearMonth + " переданы следующие показания: \n"
                                    + meterController.getUserReadingForPeriod(yearMonth));
                        }
                        case 0 -> {
                            System.out.println("Спасибо, что воспользовались нашим приложением!");
                            flag = false;
                        }
                    }
                } catch (IllegalAccessException | UserNotFoundException e) {
                    System.out.println("Логин и/или пароль введены неверно. Попробуйте ещё раз!");
                } catch (IllegalReadingChange e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Пожалуйста, следуйте инструкциям на экране для корректной работы приложения!");
                }
            }
        scanner.close();
    }
}
