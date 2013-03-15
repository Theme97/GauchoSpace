package com.GauchoSpace;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.util.ResourceLoader;

public class Shader {
	int shaderProgram = glCreateProgram();
	int vertexShader = glCreateShader(GL_VERTEX_SHADER);
	int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
	
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
		glUseProgram(enable ? shaderProgram : 0);
	}
	
	private String readFile(String resource) {
		Scanner scanner = new Scanner(ResourceLoader.getResourceAsStream(resource));
		String data = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return data;
	}
}
