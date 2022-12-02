package com.project.sessionserver.entities.value_object;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Formation {
	@Id
	private Long id;
	@NotNull(message="name  cannot be null")
	private String nom;
	private String description;

	private Double prix;
}
