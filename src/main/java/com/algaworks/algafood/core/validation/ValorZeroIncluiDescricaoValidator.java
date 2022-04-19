package com.algaworks.algafood.core.validation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		 this.valorField=constraintAnnotation.valorField();
		 this.descricaoField=constraintAnnotation.descricaoField();
		 this.descricaoObrigatoria=constraintAnnotation.descricaoObrigatoria();
	}
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Boolean valido= true;
		
		try {
			BigDecimal valor=(BigDecimal)BeanUtils.getPropertyDescriptor(value.getClass(), this.valorField).getReadMethod().invoke(value);
			String descricao =(String)BeanUtils.getPropertyDescriptor(value.getClass(), this.descricaoField).getReadMethod().invoke(value);
			
			if (valor!=null && BigDecimal.ZERO.compareTo(valor)==0 &&descricao!=null) {
				valido=descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
		} catch (Exception e) {
			throw new ValidationException(e);
			
		}
		
		return valido;
	}

}