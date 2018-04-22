package com.nguyenvanai.app.test.managers;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.nguyenvanai.app.managers.ConversionManager;

public class ConversionManagerTest {

	@Test
	public void testConvertDateToString() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String expectedResult = "21/12/2012";
		Date d = sdf.parse(expectedResult);
		String result = ConversionManager.convertDateToString(d);
		assertEquals(expectedResult, result);
	}
	
	@Test
	public void testConvertDateToStringWithoutSlashes() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String expectedResult = "21122012";
		Date d = sdf.parse("21/12/2012");
		String result = ConversionManager.convertDateToStringWithoutSlashes(d);
		assertEquals(expectedResult, result);
	}
}
