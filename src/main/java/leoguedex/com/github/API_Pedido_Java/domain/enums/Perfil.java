package leoguedex.com.github.API_Pedido_Java.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.EnumSet;

@Getter
@AllArgsConstructor
public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private int cod;
    private String descricao;

    public static Perfil toEnum(Integer cod) {
        return EnumSet.allOf(Perfil.class).stream()
                .filter(e -> e.getCod() == (cod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id inválido" + cod));
    }

}
