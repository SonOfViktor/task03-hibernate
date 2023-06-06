package ru.aston.company.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "positions", schema = "task03")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    List<Employee> employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Position position = (Position) o;
        return getName() != null && Objects.equals(getName(), position.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}