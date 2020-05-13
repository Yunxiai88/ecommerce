insert into USER (ID, EMAIL, PASSWORD, ROLE, STATUS, NAME, ADDRESS, PHONE, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY)
values (1, 'customer@gmail.com',
'$2a$10$VCvdpHnrICBspysx1vUYm.MrQ.Z14Lv77rlmuglHuc/Rc1RfI0Cu6', 'customer', 'active', 'Apple',
'10 Eunos Road 8, #03-04, Singapore Post Centre, 408600', '63485555', CURRENT_TIMESTAMP, 'apple',
CURRENT_TIMESTAMP, 'apple');

insert into USER (ID, EMAIL, PASSWORD, ROLE, STATUS, NAME, ADDRESS, PHONE, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY)
values (2, 'admin@gmail.com',
'$2a$10$VCvdpHnrICBspysx1vUYm.MrQ.Z14Lv77rlmuglHuc/Rc1RfI0Cu6', 'admin', 'active', 'Orange',
'10 Eunos Road 8, #03-04, Singapore Post Centre, 408600', '63485555', CURRENT_TIMESTAMP, 'orange',
CURRENT_TIMESTAMP, 'admin');

commit;