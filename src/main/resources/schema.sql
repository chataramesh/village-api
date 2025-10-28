INSERT INTO public.country
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name")
VALUES('dd32ccb5-0131-43bc-b8a8-c4d7a809cbb5'::uuid, '2025-10-21 23:53:15.720', 'root@gmail.com', true, '2025-10-21 23:53:15.720', 'root@gmail.com', NULL, NULL, 'INDIA');

INSERT INTO public.state
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name", country_id)
VALUES('1e5c227c-687a-4769-b98b-1483859a7cce'::uuid, '2025-10-22 09:58:13.990', 'root@gmail.com', true, '2025-10-22 09:58:13.990', 'root@gmail.com', NULL, NULL, 'Telangana', 'dd32ccb5-0131-43bc-b8a8-c4d7a809cbb5'::uuid);
INSERT INTO public.state
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name", country_id)
VALUES('0c870717-3510-4541-8aa2-0fdc7652d3e5'::uuid, NULL, NULL, true, NULL, NULL, NULL, NULL, 'Andrapradesh', 'dd32ccb5-0131-43bc-b8a8-c4d7a809cbb5'::uuid);

INSERT INTO public.district
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name", state_id)
VALUES('7a7d4a6b-de63-404b-8ebe-1c10b0689f02'::uuid, '2025-10-22 09:58:28.859', 'root@gmail.com', true, '2025-10-22 09:58:28.859', 'root@gmail.com', NULL, NULL, 'Nalgonda', '1e5c227c-687a-4769-b98b-1483859a7cce'::uuid);

INSERT INTO public.mandal
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name", district_id)
VALUES('362e5b60-7c59-4c33-81ce-86549229845d'::uuid, '2025-10-22 09:58:40.406', 'root@gmail.com', true, '2025-10-22 09:58:40.406', 'root@gmail.com', NULL, NULL, 'Marriguda', '7a7d4a6b-de63-404b-8ebe-1c10b0689f02'::uuid);
INSERT INTO public.mandal
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name", district_id)
VALUES('701586a1-60f3-43b7-87ba-32537e4c754c'::uuid, '2025-10-28 05:56:57.154', 'root@gmail.com', true, '2025-10-28 05:56:57.154', 'root@gmail.com', NULL, NULL, 'Nampally', '7a7d4a6b-de63-404b-8ebe-1c10b0689f02'::uuid);

INSERT INTO public.village
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name", mandal_id)
VALUES('679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid, NULL, 'root@gmail.com', true, NULL, 'root@gmail.com', NULL, NULL, 'MARRIGUDA', '362e5b60-7c59-4c33-81ce-86549229845d'::uuid);
INSERT INTO public.village
(id, created_at, created_by, is_active, updated_at, updated_by, code, description, "name", mandal_id)
VALUES('b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid, '2025-10-28 05:58:14.018', 'root@gmail.com', true, '2025-10-28 05:58:14.018', 'root@gmail.com', NULL, NULL, 'Nampally', '701586a1-60f3-43b7-87ba-32537e4c754c'::uuid);



INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('d4e52ae2-7050-4b1b-ad4a-2369fd7c1b32'::uuid, NULL, NULL, true, NULL, NULL, 'villageadmin@gmail.com', 'villageadmin', '$2a$10$z3sg0wueoPFJWPRlRtvb1e4yXdlshC5QZk7T2c4X/n/Q99Dm3U4fC', '12345678', 'VILLAGE_ADMIN', '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid, 0.0, 0.0);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('f84c87ee-5ed9-408d-8b04-7318b04bd622'::uuid, NULL, NULL, true, NULL, NULL, 'developer@gmail.com', 'Ramesh', '$2a$10$PnXGkpw23H1ReTST/wL5Vu6HJo6vEdh0tJQ0IaWRLifGMgBHm9Sdy', '12345678', 'SUPER_ADMIN', NULL, 0.0, 0.0);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('bd57c693-80b7-4013-8e8a-67e364e10038'::uuid, NULL, NULL, true, '2025-10-28 05:56:10.686', 'root@gmail.com', 'root@gmail.com', 'super admin', '$2a$10$2/EsE6SFSa/HiPzsvmIVFu8w3scr.ANPm5WLrz76QtVilGJ9kvyDK', '9000784494', 'SUPER_ADMIN', '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid, 17.4554933, 78.3990339);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('e02621fe-ccd6-4cac-84c5-39287cbf9d8b'::uuid, '2025-10-28 06:03:47.585', 'root@gmail.com', true, '2025-10-28 06:03:47.585', 'root@gmail.com', 'Marriguda_user1@gmail.com', 'Marriguda_user1', '$2a$10$QVHHm3xAfdoop972n.OF0OuphdP.7LfROyhAqJTZTNhf3/PSbpM26', '5656566', 'VILLAGER', '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid, 0.0, 0.0);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('1708ea70-6aa8-46ca-9273-317c806ce100'::uuid, '2025-10-28 06:05:28.068', 'root@gmail.com', true, '2025-10-28 06:05:28.068', 'root@gmail.com', 'Nampally_user1@gmail.com', 'Nampally_user1', '$2a$10$RVZaiSLpDLbw3Qbx729ptel6XY2yvCkdwqIICGifqm.2gXCXXEtSW', '89868668', 'VILLAGER', 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid, 0.0, 0.0);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('a51f19cc-1ea3-4d72-a08c-638082d22fe6'::uuid, '2025-10-28 05:59:36.853', 'root@gmail.com', true, '2025-10-28 06:07:20.564', 'Nampally_admin@gmail.com', 'Nampally_admin@gmail.com', 'Nampally_admin', '$2a$10$4ryVSl7.uLGhGQc2q4kp1OJlLfYp1k7vc0Rp/lPmXcwk3t4Db2nCO', '3262662626', 'VILLAGE_ADMIN', 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid, 17.4554933, 78.3990339);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('276ef163-6c47-4a74-9cac-37202896baec'::uuid, '2025-10-28 06:04:29.568', 'root@gmail.com', true, '2025-10-28 06:44:37.792', 'Nampally_user@gmail.com', 'Nampally_user@gmail.com', 'Nampally_user', '$2a$10$kfp.umX0DQYATu0dTMYPmuIOfuWYl15tx5b.DuZjCZpislYvzzI/2', '26565686', 'VILLAGER', 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid, 17.4554861, 78.3990567);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('627a78c4-3a95-4e38-9b96-63791514835a'::uuid, '2025-10-28 06:01:07.282', 'root@gmail.com', true, '2025-10-28 07:07:56.179', 'Marriguda_admin@gmail.com', 'Marriguda_admin@gmail.com', 'Marriguda_admin', '$2a$10$pR6tFeHGxRr8jXLmKmF3UuarytQAiDYoK1vJB5WxSBjuhbFh58zS.', '2323656565', 'VILLAGE_ADMIN', '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid, 17.4554777, 78.3990579);
INSERT INTO public.users
(id, created_at, created_by, is_active, updated_at, updated_by, email, "name", password_hash, phone, "role", village_id, latitude, longitude)
VALUES('0b28ed4e-f9ac-49a9-9ad5-91502d834521'::uuid, '2025-10-28 06:02:51.966', 'root@gmail.com', true, '2025-10-28 07:16:15.972', 'Marriguda_user@gmail.com', 'Marriguda_user@gmail.com', 'Marriguda_user', '$2a$10$UkLF0TInamKuDiFeuTu8XeAuLWOWS9hxKxvTjrOF7gD6KcTKGVGC6', '13535656', 'VILLAGER', '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid, 17.4554783, 78.3990468);



INSERT INTO public.temples
(id, created_at, created_by, is_active, updated_at, updated_by, address, caretaker_name, deity, description, email, established_year, "name", phone, priest_name, registration_number, special_days, timings, "type", owner_id, village_id)
VALUES('d6706996-1c22-4bbe-9b01-ca6972f87877'::uuid, '2025-10-28 06:37:37.784', 'Nampally_admin@gmail.com', true, '2025-10-28 06:37:37.784', 'Nampally_admin@gmail.com', 'Near primary school ,Nampally x road ', 'Yadamma', 'Anjaneyaa', 'Anjaneya temple', 'nampallyanjaneya@gmail.com', '1947', 'Lord Anjaneyaa', '1234567890', 'Nampally_user', 'Na', 'Saturday', '8:00-9:00', 'HINDU', '276ef163-6c47-4a74-9cac-37202896baec'::uuid, 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid);
INSERT INTO public.temples
(id, created_at, created_by, is_active, updated_at, updated_by, address, caretaker_name, deity, description, email, established_year, "name", phone, priest_name, registration_number, special_days, timings, "type", owner_id, village_id)
VALUES('3c36eb7c-cd72-45f6-87cb-31aa534e4a48'::uuid, '2025-10-28 07:13:10.797', 'Marriguda_admin@gmail.com', true, '2025-10-28 07:13:10.797', 'Marriguda_admin@gmail.com', 'Near vattipalli x road', 'Lakshimi', 'Shiva', 'Lord Shiva ', 'lordshivam@gmail.com', '1960', 'Lord Shiva', '1234567890', 'Marriguda_user', 'Lordshiva1234', 'Monday', '6:00 - 9:00pm', 'HINDU', '0b28ed4e-f9ac-49a9-9ad5-91502d834521'::uuid, '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid);


INSERT INTO public.schools
(id, created_at, created_by, is_active, updated_at, updated_by, address, affiliation, current_students, description, email, "name", phone, principal_name, registration_number, student_capacity, "type", website, owner_id, village_id)
VALUES('831ddb61-10a9-49dc-9078-ccf5f68a489f'::uuid, '2025-10-28 06:39:49.214', 'Nampally_admin@gmail.com', true, '2025-10-28 06:39:49.214', 'Nampally_admin@gmail.com', 'Near RRR. Function hall , Nampally bustand ', 'State Board', NULL, 'ZPHS Nampally is govt school where it''s available from 1st-10th class with full free education ', 'nampallyzphs@gmail.com', 'ZPHS Nampally', '1234567890', 'Dr.Sunil', 'N06NM2010', 500, 'SCHOOL', 'https://nampally.zphs.com', 'a51f19cc-1ea3-4d72-a08c-638082d22fe6'::uuid, 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid);
INSERT INTO public.schools
(id, created_at, created_by, is_active, updated_at, updated_by, address, affiliation, current_students, description, email, "name", phone, principal_name, registration_number, student_capacity, "type", website, owner_id, village_id)
VALUES('8d59f351-0b53-4b97-93b5-b02fde719c49'::uuid, '2025-10-28 07:14:55.852', 'Marriguda_admin@gmail.com', true, '2025-10-28 07:14:55.852', 'Marriguda_admin@gmail.com', 'Near post office marriguda old bustand', 'State Board', NULL, 'Primary school upto 5th class ', 'zpprimarym@gmail.com', 'ZP primary school', '1472583990', 'Bhikham', 'Zppm456', 50, 'SCHOOL', 'https://zppm.com', '627a78c4-3a95-4e38-9b96-63791514835a'::uuid, '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid);


INSERT INTO public.incidents
(id, created_at, created_by, is_active, updated_at, updated_by, assigned_to, category, contact_info, description, escalated_at, escalated_to, escalation_level, "location", location_type, priority, reported_by, requires_follow_up, resolution, resolved_at, status, title, urgency_reason, village_id)
VALUES('83a635ac-b3f4-4af0-a617-f8a9942c5cad'::uuid, '2025-10-28 06:32:02.054', 'Nampally_admin@gmail.com', true, '2025-10-28 06:32:02.054', 'Nampally_admin@gmail.com', NULL, 'SOCIAL', '9000784494', 'One of member in my village has critical illness and looking for financial support ', NULL, NULL, NULL, 'Nampally', 'PUBLIC_SPACE', 'HIGH', 'current_user', true, NULL, NULL, 'OPEN', 'Financial Support', 'They need to admit in. Hospital ', 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid);



INSERT INTO public.events
(id, created_at, created_by, is_active, updated_at, updated_by, description, end_time, "name", place, start_time, village_id)
VALUES('6aadee9b-f772-44f3-9115-c56a8750cfdd'::uuid, '2025-10-28 06:29:12.999', 'Nampally_admin@gmail.com', true, '2025-10-28 06:29:12.999', 'Nampally_admin@gmail.com', 'Election campaign will be starting for bielection', '2025-10-29 11:58:00.000', 'Election Campaign ', 'Near new bustand', '2025-10-28 11:58:00.000', 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid);



INSERT INTO public.entities
(id, created_at, created_by, is_active, updated_at, updated_by, address, available_slots, capacity, closing_time, contact_number, description, email, latitude, longitude, "name", opening_time, status, "type", owner_id, village_id)
VALUES('3f4bb418-8641-4e0d-bb75-1970bb1ed5d7'::uuid, '2025-10-28 06:10:06.654', 'Nampally_admin@gmail.com', true, '2025-10-28 06:10:06.654', 'Nampally_admin@gmail.com', 'Near Nampally police station,backside 1st floor', NULL, 100, '21:39:00', '1234867890', 'RRR Clinic will be available for all diagnostic services', 'rrrclinic@gmail.com', NULL, NULL, 'RRR Clinic', '09:39:00', 'OPEN', 'PHARMACY', '276ef163-6c47-4a74-9cac-37202896baec'::uuid, 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid);
INSERT INTO public.entities
(id, created_at, created_by, is_active, updated_at, updated_by, address, available_slots, capacity, closing_time, contact_number, description, email, latitude, longitude, "name", opening_time, status, "type", owner_id, village_id)
VALUES('3bb7c46e-1096-4cfd-8923-624c5fcabdb7'::uuid, '2025-10-28 06:12:06.489', 'Nampally_admin@gmail.com', true, '2025-10-28 06:12:06.489', 'Nampally_admin@gmail.com', 'Near Nampally x road ,towards marriguda road', NULL, 1000, '18:41:00', '1234988989', 'RRR Store will get all your daily usage products ', 'rrrstore@gmail.com', NULL, NULL, 'RRR Store', '08:41:00', 'OPEN', 'SHOP', '1708ea70-6aa8-46ca-9273-317c806ce100'::uuid, 'b119f39f-6b8c-4b7e-af37-88a183eeb235'::uuid);
INSERT INTO public.entities
(id, created_at, created_by, is_active, updated_at, updated_by, address, available_slots, capacity, closing_time, contact_number, description, email, latitude, longitude, "name", opening_time, status, "type", owner_id, village_id)
VALUES('2b1721a2-fc62-4e41-864c-49cd8af744db'::uuid, '2025-10-28 10:29:52.602', 'Marriguda_admin@gmail.com', true, '2025-10-28 10:29:52.602', 'Marriguda_admin@gmail.com', 'near barath petrol pump vattiplalii x road', NULL, 0, '15:59:00', '100', 'PoliceStation', 'mrgpolicestation@gmal.com', NULL, NULL, 'PoliceStation', '03:59:00', 'OPEN', 'POLICE_STATION', 'e02621fe-ccd6-4cac-84c5-39287cbf9d8b'::uuid, '679f0e5b-73b4-456f-b5e8-09afbaec99b4'::uuid);

