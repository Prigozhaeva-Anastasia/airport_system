//package com.example.course_project.runner;
//
//import com.example.course_project.entity.*;
//import com.example.course_project.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//@Component
//public class MyRunner implements CommandLineRunner {
//
//    @Autowired
//    private AirlineService airlineService;
//    @Autowired
//    private AirTicketService airTicketService;
//    @Autowired
//    private CardService cardService;
//    @Autowired
//    private DirectFlightService directFlightService;
//    @Autowired
//    private PassengerService passengerService;
//    @Autowired
//    private ReviewService reviewService;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private TransferService transferService;
//    @Autowired
//    private TransitFlightService transitFlightService;
//    @Autowired
//    private UserService userService;
//
//    public static final String ADMIN = "Admin";
//    public static final String PASSENGER = "Passenger";
//    @Override
//    public void run(String... args) throws Exception {
//        User user1 = userService.createUser("AdminA1111", "admin@gmail.com", "Смирнов", "Михаил");
//        User user2 = userService.createUser("nastyaPR2003", "prigozhaeva@gmail.com", "Пригожаева", "Анастасия");
//        User user3 = userService.createUser("vikaBU2002", "butskevich@gmail.com", "Буцкевич", "Виктория");
//        User user4 = userService.createUser("margoSM2002", "smetanchuk@gmail.com", "Сметанчук", "Маргарита");
//        User user5 = userService.createUser("andreiST1111", "stepanov@gmail.com", "Степанов", "Андрей");
//        User user6 = userService.createUser("alekseiARA1986", "artemev@gmail.com", "Артемьев", "Алексей");
//        User user7 = userService.createUser("vitaliiMLA1986", "melnik@gmail.com", "Артемьев", "Алексей");
//        User user8 = userService.createUser("annaROA1986", "romanenko@gmail.com", "Романенко", "Анна");
//        User user9 = userService.createUser("olgaOSA1990", "osadchaya@gmail.com", "Осадчая", "Ольга");
//        User user10 = userService.createUser("elenaYDA1990", "udina@gmail.com", "Юдина", "Елена");
//        User user11 = userService.createUser("maksimSHA1970", "shpilevskii@gmail.com", "Шпилевский", "Максим");
//
//        roleService.createRole(ADMIN);
//        roleService.createRole(PASSENGER);
//
//        userService.assignRoleToUser(user1.getEmail(), ADMIN);
//        userService.assignRoleToUser(user2.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user3.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user4.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user5.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user6.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user7.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user8.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user9.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user10.getEmail(), PASSENGER);
//        userService.assignRoleToUser(user11.getEmail(), PASSENGER);
//
//
//        Passenger passenger1 = passengerService.createPassenger(user2.getId(), "женский", LocalDate.parse("2003-01-30"), "RB", "HB1252370", "+375293012547");
//        Passenger passenger2 = passengerService.createPassenger(user3.getId(), "женский", LocalDate.parse("2002-11-03"), "RB", "HB8745124", "+375446254130");
//        Passenger passenger3 = passengerService.createPassenger(user4.getId(), "женский", LocalDate.parse("2001-07-21"), "RB", "HB9642370", "+375297465214");
//        Passenger passenger4 = passengerService.createPassenger(user5.getId(), "мужской", LocalDate.parse("1993-05-12"), "RB", "HB3415740", "+375296412374");
//        Passenger passenger5 = passengerService.createPassenger(user6.getId(), "мужской", LocalDate.parse("1990-02-15"), "RB", "HB6124785", "+375299654125");
//        Passenger passenger6 = passengerService.createPassenger(user7.getId(), "мужской", LocalDate.parse("1995-05-25"), "RB", "HB3201854", "+375291634127");
//        Passenger passenger7 = passengerService.createPassenger(user8.getId(), "женский", LocalDate.parse("1980-06-28"), "RB", "HB3208461", "+375291256413");
//        Passenger passenger8 = passengerService.createPassenger(user9.getId(), "женский", LocalDate.parse("1989-04-04"), "RB", "HB3052370", "+375291023712");
//        Passenger passenger9 = passengerService.createPassenger(user10.getId(), "женский", LocalDate.parse("1993-03-05"), "RB", "HB1648752", "+375291111154");
//        Passenger passenger10 = passengerService.createPassenger(user11.getId(), "мужской", LocalDate.parse("1998-09-09"), "RB", "HB6321574", "+375291010102");
//
//
//        Airline airline1 = airlineService.createAirline("Белавиа", 5, "/images/belavia.png");
//        Airline airline2 = airlineService.createAirline("Аэрофлот",4,"/images/airflot.png");
//        Airline airline3 = airlineService.createAirline("FluDubai",3.9,"/images/flueDubai.png");
//        Airline airline4 = airlineService.createAirline("Победа",4.5,"/images/pobeda.png");
//        Airline airline5 = airlineService.createAirline("Северсталь",4.9,"/images/severstal.png");
//        Airline airline6 = airlineService.createAirline("Usbekistan Airways",3,"/images/uzbekistan.png");
//        Airline airline7 = airlineService.createAirline("Wizz",5,"/images/wizzair.png");
//        Airline airline8 = airlineService.createAirline("Bulgaria Air",4.3,"/images/Bulgaria-Air.png");
//        Airline airline9 = airlineService.createAirline("British AirWays",3.8,"/images/british.png");
//
//
//        DirectFlight directFlight1 = directFlightService.createDirectFlight("HB4521", "Минск", "Минск", LocalDate.parse("2023-05-09"), LocalTime.parse("18:30"), "Домодедово","Москва", LocalDate.parse("2023-05-09"), LocalTime.parse("21:10"), 2500, airline1.getId());
//        DirectFlight directFlight2 = directFlightService.createDirectFlight("AS123", "Минск", "Минск", LocalDate.parse("2023-05-09"), LocalTime.parse("16:15"), "Ататюрк","Стамбул", LocalDate.parse("2023-05-09"), LocalTime.parse("22:40"), 4100, airline2.getId());
//        DirectFlight directFlight3 = directFlightService.createDirectFlight("PL965", "Аль Мактум", "Дубай", LocalDate.parse("2023-06-02"), LocalTime.parse("12:30"), "Минск","Минск", LocalDate.parse("2023-06-02"), LocalTime.parse("18:10"), 780, airline2.getId());
//        DirectFlight directFlight4 = directFlightService.createDirectFlight("LK654", "Минск", "Минск", LocalDate.parse("2023-07-12"), LocalTime.parse("14:20"), "Менара","Марроко", LocalDate.parse("2023-07-12"), LocalTime.parse("21:20"), 1100, airline3.getId());
//        DirectFlight directFlight5 = directFlightService.createDirectFlight("HJ2342", "Шарль-де-Голь", "Париж", LocalDate.parse("2023-08-22"), LocalTime.parse("09:25"), "Минск","Минск", LocalDate.parse("2023-08-22"), LocalTime.parse("15:36"), 1400, airline2.getId());
//        DirectFlight directFlight6 = directFlightService.createDirectFlight("YT984", "Минск", "Минск", LocalDate.parse("2023-09-13"), LocalTime.parse("08:35"), "Хитроу","Лондон", LocalDate.parse("2023-08-13"), LocalTime.parse("12:16"), 600, airline4.getId());
//        DirectFlight directFlight7 = directFlightService.createDirectFlight("AS546", "Якутск", "Якутск", LocalDate.parse("2023-10-10"), LocalTime.parse("17:10"), "Минск","Минск", LocalDate.parse("2023-10-10"), LocalTime.parse("22:10"), 1250, airline5.getId());
//        DirectFlight directFlight8 = directFlightService.createDirectFlight("QG165", "Минск", "Минск", LocalDate.parse("2023-11-16"), LocalTime.parse("11:11"), "Гейдар Алиев","Баку", LocalDate.parse("2023-11-16"), LocalTime.parse("18:15"), 1150, airline6.getId());
//        DirectFlight directFlight9 = directFlightService.createDirectFlight("KL7421", "Звартноц", "Ереван", LocalDate.parse("2023-12-02"), LocalTime.parse("14:10"), "Минск","Минск", LocalDate.parse("2023-12-02"), LocalTime.parse("22:13"), 1300, airline7.getId());
//        DirectFlight directFlight10 = directFlightService.createDirectFlight("FG8542", "Минск", "Минск", LocalDate.parse("2023-05-09"), LocalTime.parse("12:10"), "Кастелоризо","Кемер", LocalDate.parse("2023-05-09"), LocalTime.parse("14:25"), 900, airline8.getId());
//        DirectFlight directFlight11 = directFlightService.createDirectFlight("HJ9512", "Хургада", "Хургада", LocalDate.parse("2023-06-24"), LocalTime.parse("18:40"), "Минск","Минск", LocalDate.parse("2023-06-24"), LocalTime.parse("23:10"), 1110, airline9.getId());
//        DirectFlight directFlight12 = directFlightService.createDirectFlight("PL7651", "Минск", "Минск", LocalDate.parse("2023-07-20"), LocalTime.parse("11:10"), "Каир","Каир", LocalDate.parse("2023-07-20"), LocalTime.parse("19:30"), 860, airline1.getId());
//        DirectFlight directFlight13 = directFlightService.createDirectFlight("SW741", "Индиры Ганди", "Дели", LocalDate.parse("2023-08-11"), LocalTime.parse("10:10"), "Минск","Минск", LocalDate.parse("2023-08-11"), LocalTime.parse("16:30"), 1780, airline2.getId());
//        DirectFlight directFlight14 = directFlightService.createDirectFlight("SD7458", "Абакан", "Абакан", LocalDate.parse("2023-09-09"), LocalTime.parse("12:45"), "Минск","Минск", LocalDate.parse("2023-09-09"), LocalTime.parse("23:15"), 1430, airline1.getId());
//
//
//
//        TransitFlight transitFlight1 = transitFlightService.createTransitFlight("NB7415", "Нур-Султан", "Астана", LocalDate.parse("2023-05-09"), LocalTime.parse("21:10"), "Минск","Минск", LocalDate.parse("2023-05-09"), LocalTime.parse("23:49"), airline1.getId(), 2500,1);
//        TransitFlight transitFlight2 = transitFlightService.createTransitFlight("KJ6841", "Гейдар Алиев", "Баку", LocalDate.parse("2023-05-09"), LocalTime.parse("23:50"), "Звартноц","Ереван", LocalDate.parse("2023-05-10"), LocalTime.parse("03:45"), airline2.getId(), 2100,2);
//        TransitFlight transitFlight3 = transitFlightService.createTransitFlight("FG5439", "Шота Руставели", "Тбилиси", LocalDate.parse("2023-06-14"), LocalTime.parse("14:20"), "Шереметьево","Москва", LocalDate.parse("2023-06-14"), LocalTime.parse("18:30"), airline3.getId(), 2000,2);
//        TransitFlight transitFlight4 = transitFlightService.createTransitFlight("LK961", "Пулково", "Санкт-Петербург", LocalDate.parse("2023-07-17"), LocalTime.parse("17:00"), "Аль-Мактум","Дубай", LocalDate.parse("2023-07-18"), LocalTime.parse("05:30"), airline4.getId(), 1990,2);
//        TransitFlight transitFlight5 = transitFlightService.createTransitFlight("FG521", "Внуково", "Москва", LocalDate.parse("2023-08-01"), LocalTime.parse("13:13"), "Хургада","Хургада", LocalDate.parse("2023-08-02"), LocalTime.parse("01:13"), airline5.getId(), 1800,3);
//        TransitFlight transitFlight6 = transitFlightService.createTransitFlight("DF8452", "Копитнари", "Кутаиси", LocalDate.parse("2023-09-05"), LocalTime.parse("14:23"), "Батуми","Батуми", LocalDate.parse("2023-09-05"), LocalTime.parse("21:21"), airline6.getId(), 1600,2);
//        TransitFlight transitFlight7 = transitFlightService.createTransitFlight("QD6523", "Шота Руставели", "Тбилиси", LocalDate.parse("2023-10-18"), LocalTime.parse("14:15"), "Шереметьево","Москва", LocalDate.parse("2023-10-18"), LocalTime.parse("15:40"), airline7.getId(), 1100,1);
//        TransitFlight transitFlight8 = transitFlightService.createTransitFlight("LK874", "Анталия", "Анталия", LocalDate.parse("2023-11-10"), LocalTime.parse("11:00"), "Храброво","Калининград", LocalDate.parse("2023-11-10"), LocalTime.parse("20:20"), airline8.getId(), 14300,1);
//        TransitFlight transitFlight9 = transitFlightService.createTransitFlight("KH649", "Ахмета Байтусырова", "Костанай", LocalDate.parse("2023-12-12"), LocalTime.parse("19:30"), "Хитроу","Лондон", LocalDate.parse("2023-12-12"), LocalTime.parse("23:10"), airline9.getId(), 2350,2);
//        TransitFlight transitFlight10 = transitFlightService.createTransitFlight("SD854", "Севастьянова", "Сочи", LocalDate.parse("2023-05-09"), LocalTime.parse("08:00"), "Храброво","Калининград", LocalDate.parse("2023-05-09"), LocalTime.parse("17:25"), airline1.getId(), 2570,1);
//
//
//
//        AirTicket airTicket1 = airTicketService.createAirTicket(2500, 1, passenger1.getId(), directFlight1.getId(), null);
//        AirTicket airTicket2 = airTicketService.createAirTicket(4100, 14, passenger2.getId(), directFlight2.getId(), null);
//        AirTicket airTicket3 = airTicketService.createAirTicket(780, 21, passenger1.getId(), directFlight3.getId(), null);
//        AirTicket airTicket4 = airTicketService.createAirTicket(1100, 13, passenger3.getId(), directFlight4.getId(), null);
//        AirTicket airTicket5 = airTicketService.createAirTicket(1400, 31, passenger4.getId(), directFlight5.getId(), null);
//        AirTicket airTicket6 = airTicketService.createAirTicket(600, 16, passenger5.getId(), directFlight6.getId(), null);
//        AirTicket airTicket7 = airTicketService.createAirTicket(1250, 22, passenger9.getId(), directFlight7.getId(), null);
//        AirTicket airTicket8 = airTicketService.createAirTicket(1150, 17, passenger10.getId(), directFlight8.getId(), null);
//        AirTicket airTicket9 = airTicketService.createAirTicket(1300, 9, passenger6.getId(), directFlight9.getId(), null);
//        AirTicket airTicket10 = airTicketService.createAirTicket(900, 6, passenger2.getId(), directFlight10.getId(), null);
//        AirTicket airTicket11 = airTicketService.createAirTicket(1110, 11, passenger7.getId(), directFlight11.getId(), null);
//        AirTicket airTicket12 = airTicketService.createAirTicket(860, 2, passenger4.getId(), directFlight12.getId(), null);
//        AirTicket airTicket13 = airTicketService.createAirTicket(1780, 4, passenger8.getId(), directFlight13.getId(), null);
//        AirTicket airTicket14 = airTicketService.createAirTicket(1430, 3, passenger2.getId(), directFlight14.getId(), null);
//        AirTicket airTicket15 = airTicketService.createAirTicket(2500, 8, passenger1.getId(), null, transitFlight1.getId());
//        AirTicket airTicket16 = airTicketService.createAirTicket(2100, 25, passenger2.getId(), null, transitFlight2.getId());
//        AirTicket airTicket17 = airTicketService.createAirTicket(2000, 26, passenger3.getId(), null, transitFlight3.getId());
//        AirTicket airTicket18 = airTicketService.createAirTicket(1990, 34, passenger2.getId(), null, transitFlight4.getId());
//        AirTicket airTicket19 = airTicketService.createAirTicket(1800, 37, passenger5.getId(), null, transitFlight5.getId());
//        AirTicket airTicket20 = airTicketService.createAirTicket(1600, 38, passenger6.getId(), null, transitFlight6.getId());
//        AirTicket airTicket21 = airTicketService.createAirTicket(1100, 39, passenger1.getId(), null, transitFlight7.getId());
//        AirTicket airTicket22 = airTicketService.createAirTicket(14300, 12, passenger8.getId(), null, transitFlight8.getId());
//        AirTicket airTicket23 = airTicketService.createAirTicket(11750, 41, passenger9.getId(), null, transitFlight9.getId());
//        AirTicket airTicket24 = airTicketService.createAirTicket(12850, 42, passenger10.getId(), null, transitFlight10.getId());
//
//
//        Card card1 = cardService.createCard(8745963212458756l, 11, 2026, 1120, passenger1.getId());
//        Card card2 = cardService.createCard(7452146325874126l, 1, 2030, 1420, passenger2.getId());
//        Card card3 = cardService.createCard(2103574210325694l, 2, 2031, 2720, passenger3.getId());
//        Card card4 = cardService.createCard(8421657420136541l, 4, 2024, 1920, passenger4.getId());
//        Card card5 = cardService.createCard(9457612302584512l, 5, 2029, 2420, passenger5.getId());
//        Card card6 = cardService.createCard(3021654785412965l, 6, 2032, 2850, passenger6.getId());
//        Card card7 = cardService.createCard(1023641258746325l, 7, 2034, 1850, passenger7.getId());
//        Card card8 = cardService.createCard(1111985475623145l, 10, 2029, 1220, passenger8.getId());
//        Card card9 = cardService.createCard(6565985478541254l, 9, 2028, 2850, passenger9.getId());
//        Card card10 = cardService.createCard(8302697125478654l, 12, 2026, 2450, passenger10.getId());
//
//
//        Review review1 = reviewService.createReview(airline1.getId(), passenger1.getId(), 5, 5, 4, 4, 5, "good");
//        Review review2 = reviewService.createReview(airline2.getId(), passenger2.getId(), 4, 3, 4,4, 4, "cool");
//        Review review3 = reviewService.createReview(airline3.getId(), passenger3.getId(), 5, 5, 3, 4, 5, "good");
//        Review review4 = reviewService.createReview(airline4.getId(), passenger4.getId(), 5, 5, 4, 4, 5, "good");
//        Review review5 = reviewService.createReview(airline5.getId(), passenger5.getId(), 5, 4, 4, 3, 5, "good");
//        Review review6 = reviewService.createReview(airline6.getId(), passenger6.getId(), 5, 4, 4, 3, 5, "good");
//        Review review7 = reviewService.createReview(airline7.getId(), passenger7.getId(), 5, 5, 4, 4, 5, "good");
//        Review review8 = reviewService.createReview(airline8.getId(), passenger8.getId(), 5, 4, 4, 4, 5, "good");
//        Review review9 = reviewService.createReview(airline9.getId(), passenger9.getId(), 3, 5, 4, 4, 5, "good");
//        Review review10 = reviewService.createReview(airline1.getId(), passenger9.getId(), 5, 5, 4, 4, 5, "good");
//        Review review11 = reviewService.createReview(airline2.getId(), passenger10.getId(), 4, 3, 4,4, 4, "cool");
//        Review review12 = reviewService.createReview(airline3.getId(), passenger8.getId(), 5, 5, 3, 4, 5, "good");
//        Review review13 = reviewService.createReview(airline4.getId(), passenger7.getId(), 5, 5, 4, 4, 5, "good");
//        Review review14 = reviewService.createReview(airline5.getId(), passenger6.getId(), 5, 4, 4, 3, 5, "good");
//        Review review15 = reviewService.createReview(airline1.getId(), passenger4.getId(), 5, 5, 4, 4, 5, "good");
//        Review review16 = reviewService.createReview(airline2.getId(), passenger5.getId(), 4, 3, 4,4, 4, "cool");
//
//
//
//        Transfer transfer1 = transferService.createTransfer("Минск", LocalTime.parse("22:30"), transitFlight1.getId());
//        Transfer transfer2 = transferService.createTransfer("Минск", LocalTime.parse("02:10"), transitFlight2.getId());
//        Transfer transfer3 = transferService.createTransfer("Якутск", LocalTime.parse("03:00"), transitFlight2.getId());
//        Transfer transfer4 = transferService.createTransfer("Минск", LocalTime.parse("16:15"), transitFlight3.getId());
//        Transfer transfer5 = transferService.createTransfer("Самара", LocalTime.parse("17:45"), transitFlight3.getId());
//        Transfer transfer6 = transferService.createTransfer("Минск", LocalTime.parse("19:40"), transitFlight4.getId());
//        Transfer transfer7 = transferService.createTransfer("Стамбул", LocalTime.parse("23:25"), transitFlight4.getId());
//        Transfer transfer8 = transferService.createTransfer("Минск", LocalTime.parse("15:10"), transitFlight5.getId());
//        Transfer transfer9 = transferService.createTransfer("Астана", LocalTime.parse("18:30"), transitFlight5.getId());
//        Transfer transfer10 = transferService.createTransfer("Анталия", LocalTime.parse("22:50"), transitFlight5.getId());
//        Transfer transfer11 = transferService.createTransfer("Минск", LocalTime.parse("16:50"), transitFlight6.getId());
//        Transfer transfer12 = transferService.createTransfer("Москва", LocalTime.parse("20:20"), transitFlight6.getId());
//        Transfer transfer13 = transferService.createTransfer("Минск", LocalTime.parse("15:00"), transitFlight7.getId());
//        Transfer transfer14 = transferService.createTransfer("Минск", LocalTime.parse("17:10"), transitFlight8.getId());
//        Transfer transfer15 = transferService.createTransfer("Минск", LocalTime.parse("20:45"), transitFlight9.getId());
//        Transfer transfer16 = transferService.createTransfer("Yakutsk", LocalTime.parse("21:25"), transitFlight9.getId());
//        Transfer transfer17 = transferService.createTransfer("Минск", LocalTime.parse("12:10"), transitFlight10.getId());
//
//
//    }
//}
