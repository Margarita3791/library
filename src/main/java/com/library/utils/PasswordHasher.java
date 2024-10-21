package com.library.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Список паролей, которые нужно хэшировать
        String[] passwords = {"01122004"}; // добавьте сюда остальные пароли

        for (String password : passwords) {
            String hashedPassword = passwordEncoder.encode(password);
            System.out.println(hashedPassword); // вывод хэшированного пароля в консоль
        }
    }
}
