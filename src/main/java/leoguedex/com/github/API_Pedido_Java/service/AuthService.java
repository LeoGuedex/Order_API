package leoguedex.com.github.API_Pedido_Java.service;

import leoguedex.com.github.API_Pedido_Java.domain.entity.Cliente;
import leoguedex.com.github.API_Pedido_Java.domain.repository.ClienteRepository;
import leoguedex.com.github.API_Pedido_Java.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    public void sendNewPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
        clienteRepository.save(cliente);
        emailService.sendNewPassword(cliente, newPass);
    }

    private String newPassword() {
        return IntStream.range(0, 10)
                .mapToObj(i -> String.valueOf(randonChar()))
                .collect(Collectors.joining());
    }


    private char randonChar() {
        int opt = ThreadLocalRandom.current().nextInt(3);
        switch (opt) {
            case 0:
                return (char) (ThreadLocalRandom.current().nextInt(10) + 48);
            case 1:
                return (char) (ThreadLocalRandom.current().nextInt(26) + 65);
            default:
                return (char) (ThreadLocalRandom.current().nextInt(26) + 97);
        }
    }

}
