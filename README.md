Александр Юрьевич!!!
Я только сейчас заметил, что target с рабочим jar не попал в проект. 
Для того чтобы сделать jar я использую команду mvn install, однако это еще не все. 
Необходимо базу данных database.db добавить в target, прямо рядом с jar.
Иначе все запустится, но страницы использующие таблицы не будут работать.
