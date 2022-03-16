package it.epicode.be.energy.util;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

public class LocalDateConverter implements Converter<String, LocalDate> {

	@Override
	public LocalDate convert(String data) {
		return LocalDate.parse(data);
	}

}
