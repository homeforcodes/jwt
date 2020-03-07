package com.sys.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseEntity {

	private int code;
	private String msg;
	private Object data;
}
