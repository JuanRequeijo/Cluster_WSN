package projects.wsneeFD.nodes.messages;

import java.util.Vector;

/**
 * Class (structure) to store important data from sersors readings, like: <p>
 * char type (Type of sensor data, e.g.: t=Temp., h=Hum., l=Lum. or v=Volt.), <br>
 * double value (Absolute value), <br>
 * double time (Date/time from value reading), <br> 
 * double batLevel (Battery power level sensor) and <br> 
 * int round (Round number) 
 * @author Fernando Rodrigues
 * 
 */

public class DataRecordItens
{
	public Vector<DataRecord> dataRecords;

	private boolean nonRead = true;
	
	private int[][] types2;
	
	private double[][] values2;
	
	private double[] times;
	
	private double[] batLevels;
	
	private int[] rounds;
	

	/**
	 * Retorna o tamanho do "dataRecords" contido neste "DataRecordItens"
	 * @return tamanho do dataRecords
	 */
	public int size() {
		return dataRecords.size();
	} // end size()
	
	/**
	 * Returns the element at the specified position in "dataRecords" Vector.
	 * @param i index of element to return
	 * @return object at the specified index
	 */
	public DataRecord get(int i) {
		return dataRecords.get(i);
	} // end get(int i)
	
	/**
	 * Atualiza o "dataRecords" estrutura do "currentNode" pela leitura de "windowSize" quantidade de dados do tipo "dataType" <p>
	 * [Eng] Updates the "dataRecords" structure from the "currentNode" by the reading of "windowSize" quantity of data from type "dataType"
	 * @param currentNode Sensor node to have the dataRecords updated
	 * @param dataTypes Types of data to be read by sensor
	 */
//	private void updatedataRecords(SimpleNode currentNode, int[] dataTypes, int windowSize) {
//		for (int i=0; i < windowSize; i++) {
//			currentNode.dataRecords.add(getData(currentNode, dataTypes));
//		}
//		while (currentNode.dataRecords.size() > windowSize) {
//			currentNode.dataRecords.removeElementAt(0);
//		}
//	}

	/**
	 * Adiciona os respectivos valores para o atributo dataRecords do sensor (SimpleNode)<p>[Eng] Adds the respective values to dataRecords attribute from this sensor (SimpleNode)
	 * @param typ Tipo de dado do sensor, como t=Temp., h=Hum., l=Lum. or v=Volt.<p>[Eng] Type of sensor data, like t=Temp., h=Hum., l=Lum. or v=Volt.
	 * @param val Valor Absoluto<p>[Eng] Absolute value
	 * @param tim Data/Tempo do valor lido no formato double<p> [Eng] Date/time from value reading in double format
	 * @param bat Nível de Potência da bateria no sensor<p>[Eng] Battery power level sensor
	 * @param rnd Número do round<p>[Eng] Round number
	 */
	public void add(int[] typs, double[] vals, double tim, double bat, int rnd, int windowSize)
	{
		if (this.dataRecords == null) {
			this.dataRecords = new Vector<DataRecord>();
		}
		DataRecord dr = new DataRecord();
		
		dr.typs = typs;
		dr.values = vals;
		dr.time = tim;
		dr.batLevel = bat;
		dr.round = rnd;
		
		dataRecords.add(dr);
		//Implements a FIFO structure with the vector 'dataRecords' with at most 'slidingWindowSize' elements
		while (dataRecords.size() > windowSize)
		{
			dataRecords.removeElementAt(0);
		}
		nonRead = true;
	} // end add(char typ, double val, double tim, double bat, int rnd, int windowSize)

	/**
	 * Adiciona os respectivos valores para o atributo dataRecords do sensor (SimpleNode)<p>[Eng] Adds the respective values to dataRecords attribute from this sensor (SimpleNode)
	 * @param dataRecord O registo de dados com os dados a serem adicionados ao "dataRecords" vector a partir do sensor atual <p> [Eng] Data record with the data to be add to "dataRecords" vector from the current sensor
	 * @param slidingWindowSize Indica o tamanho da janela de dados a serem mantidos
	 */	
	public void add(DataRecord dataRecord, int slidingWindowSize)
	{
		if (dataRecord == null) {
			return;
		}

		if (this.dataRecords == null)
		{
			this.dataRecords = new Vector<DataRecord>();
		}
		
		dataRecords.add(dataRecord);
		
		//Implements a FIFO structure with the vector 'dataRecords' with at most 'slidingWindowSize' elements
		while (dataRecords.size() > slidingWindowSize)
		{
			dataRecords.removeElementAt(0);
		}
		nonRead = true;
	} // end adddataRecords(char typ, double val, double tim, double bat, int rnd)

	
	private void readData()
	{
		if (nonRead)
		{
			int tam = 0;
			if (dataRecords != null)
			{
				tam = dataRecords.size();
			}
			types2 = new int[tam][];
			values2 = new double[tam][];
			times = new double[tam];
			batLevels = new double[tam];
			rounds = new int[tam];
			
			for (int i=0; i<tam; i++)
			{
				if (dataRecords.get(i) != null)
				{
					types2[i] = ((DataRecord)dataRecords.get(i)).typs;
					values2[i] = ((DataRecord)dataRecords.get(i)).values;
					times[i] = ((DataRecord)dataRecords.get(i)).time;
					batLevels[i] = ((DataRecord)dataRecords.get(i)).batLevel;
					rounds[i] = ((DataRecord)dataRecords.get(i)).round;
				}
				else
				{
					types2[i] = null;
					values2[i] = null;
					times[i] = 0.0;
					batLevels[i] = 0.0;
					rounds[i] = 0;
				}
			}
			
			nonRead = false;
		}
	}
	
	public int[] getDataRecordTypes(int ind)
	{
		readData();
		return types2[ind];
	}
	
	public double[] getDataRecordValues(int ind)
	{
		readData();
		return values2[ind];
	}

	public double[] getDataRecordTimes()
	{
		readData();
		return times;
	}

	public double[] getDataRecordBatLevels()
	{
		readData();
		return batLevels;
	}
	
	public int[] getDataRecordRounds()
	{
		readData();
		return rounds;
	}
	
	
} // end dataRecords