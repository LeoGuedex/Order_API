package leoguedex.com.github.API_Pedido_Java.rest.dto;

import leoguedex.com.github.API_Pedido_Java.service.validation.ClientInsert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ClientInsert
@NoArgsConstructor
@AllArgsConstructor
public class ClienteNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    @Length(min = 5, max = 120, message = "Tamanho deve ser entre 5 e 120")
    private String nome;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    @Email(message = "E-mail inválido")
    private String email;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    private String cpfCnpj;

    private Integer tipo;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    private String senha;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    private String logradouro;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    private String numero;

    private String complemento;

    private String bairro;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    private String cep;

    private Integer cidadeId;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    private String telefone1;

    private String telefone2;

    private String telefone3;

}
