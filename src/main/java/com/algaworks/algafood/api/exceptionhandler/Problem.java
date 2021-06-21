package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@Getter
	@Builder
	public static class Objects {
		private String name;
		private String userMessage;
	}

	private String detail;
	private List<Objects> objects;
	private Integer status;

	private OffsetDateTime timestamp;
	private String title;
	private String type;

	private String userMessage;

}
