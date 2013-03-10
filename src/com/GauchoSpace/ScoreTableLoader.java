package com.GauchoSpace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class ScoreTableLoader {
	private String filePath;
	
	public ScoreTableLoader(String filePath) throws FileNotFoundException,
			IOException{
		this.filePath = filePath;
	}
	
	// Scans save file and assigns its names and values into an ArrayList
    public ArrayList<ScoreRecord> loadScoreTable() throws FileNotFoundException, IOException {
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<ScoreRecord> scoreList = new ArrayList<ScoreRecord>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
        	String[] coords = line.split(" ");
        	String name = coords[0];
        	String value = coords[1];
        	ScoreRecord scoreRec = new ScoreRecord(name, new Integer(value));
        	scoreList.add(scoreRec);}
        bufferedReader.close();
        Collections.sort(scoreList);
        return scoreList;
    }
    
    // Rewrites the save file with the new score
    public void saveScoreTable(ArrayList<ScoreRecord> scores) throws IOException {
        if (scores != null){
        	FileWriter fileWriter = new FileWriter(filePath);
        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        	for (ScoreRecord scoreRecord: scores){
        		bufferedWriter.write(scoreRecord.getName() + " " + scoreRecord.getPoints() + "\n");
        	}
        	bufferedWriter.close();
        }
    }
}
