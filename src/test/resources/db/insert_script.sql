INSERT INTO users (id, email, first_name, last_name, password) VALUES (1, 'prigozhaeva@gmail.com', 'Anastasia', 'Prigozhaeva', 'nastya2003');
INSERT INTO users (id, email, first_name, last_name, password) VALUES (2, 'butskevich@gmail.com', 'Victoria', 'Butskevich', 'vika2002');
INSERT INTO users (id, email, first_name, last_name, password) VALUES (3, 'smirnov@gmail.com', 'Mikhail', 'Smirnov', 'mikhail1986');

INSERT INTO roles (id, role_name) VALUES (1, 'Admin');
INSERT INTO roles (id, role_name) VALUES (2, 'Passenger');

INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);

INSERT INTO passengers (id, citizenship, date_of_birth, gender, passport_number, phone_number, user_id) VALUES (1, 'RB', '2023-04-12', 'female', 'HB3052370', '+375291023712', 1);
INSERT INTO passengers (id, citizenship, date_of_birth, gender, passport_number, phone_number, user_id) VALUES (2, 'RB', '2023-04-12', 'female', 'HB8745124', '+375447241135', 2);

INSERT INTO airlines (id, airline_name, path_to_img, rating) VALUES (1, 'Belavia', '/images/belavia.png', 5);
INSERT INTO airlines (id, airline_name, path_to_img, rating) VALUES (2, 'Aeroflot', '/images/airflot.png', 4);

INSERT INTO direct_flights (id, arrival_airport, arrival_city, arrival_date, arrival_time, departure_airport, departure_city, departure_date, departure_time, flight_number, price_of_ticket, airline_id) VALUES (1, 'Sheremetevo', 'Moscow', '2023-04-12', '13:58:39', 'Ataturk', 'Stambul', '2023-04-12', '13:58:39', 'AS123', 1500, 1);
INSERT INTO direct_flights (id, arrival_airport, arrival_city, arrival_date, arrival_time, departure_airport, departure_city, departure_date, departure_time, flight_number, price_of_ticket, airline_id) VALUES (2, 'Hitrou', 'London', '2023-04-12', '13:58:39', 'Menara', 'Morokko', '2023-04-12', '13:58:39', 'HG723', 2500, 2);

INSERT INTO transit_flights (id, arrival_airport, arrival_city, arrival_date, arrival_time, departure_airport, departure_city, departure_date, departure_time, flight_number, price_of_ticket, airline_id, count_of_transfers) VALUES (3, 'Minsk', 'Minsk', '2023-04-12', '13:58:39', 'Shal-de-Gol', 'Paris', '2023-04-12', '13:58:39', 'QW8547', 1, 1, 2500);
INSERT INTO transit_flights (id, arrival_airport, arrival_city, arrival_date, arrival_time, departure_airport, departure_city, departure_date, departure_time, flight_number, price_of_ticket, airline_id, count_of_transfers) VALUES (4, 'Yakutsk', 'Yakutsk', '2023-04-12', '13:58:39', 'Ataturk', 'Stambul', '2023-04-12', '13:58:39', 'QA6547', 2, 2, 2900);

INSERT INTO air_tickets (id, price, seat_number, type_of_ticket, direct_flight_id, passenger_id, transit_flight_id) VALUES (1, 1500, 40, 'econom-class', 1, 1, NULL);
INSERT INTO air_tickets (id, price, seat_number, type_of_ticket, direct_flight_id, passenger_id, transit_flight_id) VALUES (2, 2500, 45, 'econom-class', NULL, 1, 3);
INSERT INTO air_tickets (id, price, seat_number, type_of_ticket, direct_flight_id, passenger_id, transit_flight_id) VALUES (3, 2500, 21, 'econom-class', 2, 2, NULL);
INSERT INTO air_tickets (id, price, seat_number, type_of_ticket, direct_flight_id, passenger_id, transit_flight_id) VALUES (4, 2900, 13, 'econom-class', NULL, 2, 4);

INSERT INTO cards (id, balance, month, number, year, passenger_id) VALUES (1, 120, 11, 8745963212458756, 2009, 1);
INSERT INTO cards (id, balance, month, number, year, passenger_id) VALUES (2, 520, 12, 8745963217465215, 2016, 2);

INSERT INTO reviews (id, check_in_speed, crew_work, overall_score, quality_of_flight_meals, remark, state_of_salon, airline_id, passenger_id) VALUES (1, 0, 0, 0, 0, NULL, 0, 1, 1);
INSERT INTO reviews (id, check_in_speed, crew_work, overall_score, quality_of_flight_meals, remark, state_of_salon, airline_id, passenger_id) VALUES (2, 0, 0, 0, 0, NULL, 0, 2, 2);

INSERT INTO transfers (id, arrival_time, name_of_city, transit_flight_id) VALUES (1, '13:58:39', 'Minsk', 3);
INSERT INTO transfers (id, arrival_time, name_of_city, transit_flight_id) VALUES (2, '13:58:39', 'Moscow', 4);
INSERT INTO transfers (id, arrival_time, name_of_city, transit_flight_id) VALUES (3, '13:58:39', 'Yakutsk', 4);