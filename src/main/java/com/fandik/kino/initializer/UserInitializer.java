package com.fandik.kino.initializer;

import com.fandik.kino.entities.UserEntity;
import com.fandik.kino.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        UserEntity admin = new UserEntity();
        UserEntity fanda = new UserEntity();
        UserEntity lojza = new UserEntity();

        admin.setId(1L);
        admin.setName("Administrátor Adminovič");
        admin.setLogin("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(UserEntity.Role.ROLE_ADMIN);

        fanda.setId(2L);
        fanda.setName("Fandík Křivanců");
        fanda.setLogin("fanda");
        fanda.setPassword(passwordEncoder.encode("heslo"));
        fanda.setRole(UserEntity.Role.ROLE_USER);

        lojza.setId(3L);
        lojza.setName("Lojzík Malej");
        lojza.setLogin("lojza");
        lojza.setPassword(passwordEncoder.encode("heslo"));
        lojza.setRole(UserEntity.Role.ROLE_USER);

        userRepository.saveAll(List.of(admin, fanda, lojza));
    }
}
