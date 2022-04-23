package qx.leizige.convert.build;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateEntity implements Serializable {


    private Long id;

    private String oldName;

    private String newName;

    private Boolean array;

    private Long parentId;


}
