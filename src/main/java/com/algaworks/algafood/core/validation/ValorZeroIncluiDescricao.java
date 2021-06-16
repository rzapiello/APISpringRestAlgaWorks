package com.algaworks.algafood.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy =  {ValorZeroIncluiDescricaoValidator.class})
public @interface ValorZeroIncluiDescricao {
	
	String descricaoField();

	String descricaoObrigatoria();

	Class<?>[] groups() default { };
	
	String message() default "Descrição obrigatória inválida";
	Class<? extends Payload>[] payload() default { };
	String valorField();
}
