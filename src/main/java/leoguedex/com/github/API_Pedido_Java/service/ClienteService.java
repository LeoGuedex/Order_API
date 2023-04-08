package leoguedex.com.github.API_Pedido_Java.service;

import leoguedex.com.github.API_Pedido_Java.domain.entity.Cidade;
import leoguedex.com.github.API_Pedido_Java.domain.entity.Cliente;
import leoguedex.com.github.API_Pedido_Java.domain.entity.Endereco;
import leoguedex.com.github.API_Pedido_Java.domain.enums.Perfil;
import leoguedex.com.github.API_Pedido_Java.domain.enums.TipoCliente;
import leoguedex.com.github.API_Pedido_Java.domain.repository.ClienteRepository;
import leoguedex.com.github.API_Pedido_Java.domain.repository.EnderecoRepository;
import leoguedex.com.github.API_Pedido_Java.exception.AuthorizationException;
import leoguedex.com.github.API_Pedido_Java.exception.DataIntegratyException;
import leoguedex.com.github.API_Pedido_Java.exception.ObjectNotFoundException;
import leoguedex.com.github.API_Pedido_Java.rest.dto.ClienteDTO;
import leoguedex.com.github.API_Pedido_Java.rest.dto.ClienteNewDTO;
import leoguedex.com.github.API_Pedido_Java.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();

        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto Não Encontrado! Id: " + id + ", tipo: "
                + Cliente.class.getName()));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return clienteRepository.findAll(pageRequest);
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        Cliente clienteSaved = clienteRepository.save(cliente);
        enderecoRepository.save(clienteSaved.getEnderecos().get(0));

        return clienteSaved;
    }

    public Cliente update(Cliente cliente) {
        Cliente clienteToUpdate = find(cliente.getId());
        updateData(clienteToUpdate, cliente);

        return clienteRepository.save(clienteToUpdate);
    }

    public Cliente fromDto(ClienteDTO clienteDto) {
        return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null, null);
    }

    public Cliente fromDto(ClienteNewDTO clienteNewDto) {
        Cliente cliente = new Cliente(
                null,
                clienteNewDto.getNome(),
                clienteNewDto.getEmail(),
                clienteNewDto.getCpfCnpj(),
                Optional.ofNullable(clienteNewDto.getTipo())
                        .map(TipoCliente::toEnum)
                        .orElseThrow(() -> new IllegalArgumentException("Tipo de cliente inválido")),
                bCryptPasswordEncoder.encode(clienteNewDto.getSenha())
        );

        Cidade cidade = new Cidade(clienteNewDto.getCidadeId(), null, null);

        Endereco endereco = new Endereco(
                null,
                clienteNewDto.getLogradouro(),
                clienteNewDto.getNumero(),
                clienteNewDto.getComplemento(),
                clienteNewDto.getBairro(),
                clienteNewDto.getCep(),
                cliente,
                cidade
        );

        cliente.getEnderecos().add(endereco);

        Optional.ofNullable(clienteNewDto.getTelefone1()).ifPresent(cliente.getTelefones()::add);
        Optional.ofNullable(clienteNewDto.getTelefone2()).ifPresent(cliente.getTelefones()::add);
        Optional.ofNullable(clienteNewDto.getTelefone3()).ifPresent(cliente.getTelefones()::add);

        return cliente;
    }


    public void delete(Integer id) {
        find(id);

        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegratyException("Não é possivel excluir um cliente que possui dados vinculados.");
        }
    }

    public void updateData(Cliente clienteToUpdate, Cliente cliente) {
        clienteToUpdate.setNome(cliente.getNome());
        clienteToUpdate.setEmail(cliente.getEmail());
    }

}
