package br.com.rentacar.rentacarapi.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_make")
@SequenceGenerator(name="tb_make_id_seq", sequenceName ="tb_make_id_seq", allocationSize = 1)
@Where(clause="deleted_tmsp is null")
@SQLDelete(sql = "UPDATE tb_make SET deleted_tmsp = now() WHERE id=?")
public class Make {

    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tb_make_id_seq")
    @Id
    private Long id;
    @Column(name = "name", unique = true)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdByName;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_tmsp")
    private LocalDateTime deletedTmsp;

    public Make() {
    }
}
