package leoguedex.com.github.API_Pedido_Java.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.EnumSet;

@Getter
@AllArgsConstructor
public enum TipoCliente {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Juridica");

    private int cod;
    private String descricao;

    public static TipoCliente toEnum(Integer cod) {
        return EnumSet.allOf(TipoCliente.class).stream()
                .filter(e -> e.getCod() == (cod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id inválido" + cod));
    }

}
