package br.com.rentacar.rentacarapi.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_car")
@Where(clause="deleted_tmsp is null")
@SQLDelete(sql = "UPDATE tb_car SET deleted_tmsp = now() WHERE id=?")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="make_id", nullable = false)
    private Make make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "available", nullable = false)
    private Boolean available = false;

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

}
