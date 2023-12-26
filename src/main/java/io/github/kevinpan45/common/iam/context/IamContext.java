package io.github.kevinpan45.common.iam.context;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IamContext {
    private String sub;
    private String username;
    private String email;
}
