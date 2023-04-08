package leoguedex.com.github.API_Pedido_Java.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.EnumSet;

@Getter
@AllArgsConstructor
public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String descricao;

    public static EstadoPagamento toEnum(Integer cod) {
        return EnumSet.allOf(EstadoPagamento.class).stream()
                .filter(e -> e.getCod() == (cod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id inválido" + cod));
    }

}
