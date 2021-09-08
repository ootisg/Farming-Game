package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;

public class SaveFile {
	
	private String fileName;
	
	private HashMap<String, SaveNode> saveData;
	
	public SaveFile () {
		this.fileName = null;
		saveData = new HashMap<String, SaveNode> ();
	}
	
	public SaveFile (String fileName) {
		this.fileName = fileName;
		setFile (fileName);
	}
	
	private class SaveNode {
		
		private String objId;
		private String mapName;
		private String data;
		
		public SaveNode (String data) {
			String[] parsed = data.split (":");
			if (parsed.length == 2) {
				this.objId = parsed [0];
				this.data = parsed [1];
			}
		}
		
		public SaveNode (String objId, String data) {
			this.objId = objId;
			this.mapName = objId.split (",")[0];
			this.data = data;
		}
		
		public void setData (String data) {
			this.data = data;
		}
		
		public String getObjId () {
			return objId;
		}
		
		public String getMapName () {
			return mapName;
		}
		
		public String getData () {
			return data;
		}
		
		@Override
		public String toString () {
			return objId + ":" + data;
		}
	}
	
	public void save (String objId, String data) {
		if (objId == null || data == null) {
			Thread.dumpStack ();
		}
		if (saveData.containsKey (objId)) {
			saveData.get (objId).setData (data);
		} else {
			saveData.put (objId, new SaveNode (objId, data));
		}
	}
	
	public String getSaveData (String objId) {
		SaveNode node = saveData.get (objId);
		return node == null ? null : node.getData ();
	}
	
	public void writeToFile () {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter (new File (fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<Entry<String, SaveNode>> iter = saveData.entrySet ().iterator ();
		while (iter.hasNext ()) {
			SaveNode working = iter.next ().getValue ();
			if (iter.hasNext ()) {
				writer.println (working);
			} else {
				writer.print (working);
			}
		}
		writer.close ();
	}
	
	public String getFileName () {
		return fileName;
	}
	
	public void setFile (String fileName) {
		this.fileName = fileName;
		saveData = new HashMap<String, SaveNode> ();
		File workingFile = new File (fileName);
		try {
			Scanner fileScanner = new Scanner (workingFile);
			while (fileScanner.hasNextLine ()) {
				SaveNode curr = new SaveNode (fileScanner.nextLine ());
				saveData.put (curr.getObjId(), curr);
			}
			fileScanner.close ();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
