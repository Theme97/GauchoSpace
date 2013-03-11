package com.GauchoSpace.Levels;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Shader {
	int shaderProgram = glCreateProgram();
	int vertexShader = glCreateShader(GL_VERTEX_SHADER);
	int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
	
	int oldProgram;
	
	public Shader(String vertexShaderFile, String fragmentShaderFile) {
		glShaderSource(vertexShader, readFile(vertexShaderFile));
		glCompileShader(vertexShader);
		if (glGetShader(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Error compiling vertex shader");
		}
		
		glShaderSource(fragmentShader, readFile(fragmentShaderFile));
		glCompileShader(fragmentShader);
		if (glGetShader(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Error compiling fragment shader");
		}
		
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
	}
	
	public void use(boolean enable) {
		//glUseProgram(enable ? shaderProgram : 0);
		if (enable) {
			oldProgram = glGetInteger(GL_CURRENT_PROGRAM);
			glUseProgram(shaderProgram);
		} else {
			glUseProgram(oldProgram);
		}
	}
	
	private String readFile(String filename) {
		try {
			return new Scanner(new File(filename)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
