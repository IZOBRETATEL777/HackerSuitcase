package az.edu.arti.projct.zero.hackersuitcase.service;

import az.edu.arti.projct.zero.hackersuitcase.dao.entity.User;
import az.edu.arti.projct.zero.hackersuitcase.dao.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
