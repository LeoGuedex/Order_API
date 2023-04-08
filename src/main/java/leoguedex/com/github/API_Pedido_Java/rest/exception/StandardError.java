package leoguedex.com.github.API_Pedido_Java.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {

    private static final long serialVersionUID= 1L;

    private Integer status;
    private String msg;
    private Long timeStamp;

}
