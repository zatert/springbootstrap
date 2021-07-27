package app.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Override
    public String toString() {
        return ""+ name.substring(5) +"";
    }
}
