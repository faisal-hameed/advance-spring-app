package pk.habsoft.demo.estore.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    /*
     * Id will be provided.
     */
    @Id
    private Long id;

    @NotNull
    @Column(name = "code", unique = true)
    private String code;

    @NotNull
    @Column(name = "label")
    private String label;

    @Column(name = "is_internal")
    private boolean isInternal;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<PermissionEntity> permissions = new ArrayList<>();

    @Override
    public String toString() {
        return "RoleEntity [id=" + id + ", code=" + code + ", label=" + label + ", isInternal=" + isInternal + "]";
    }

}
