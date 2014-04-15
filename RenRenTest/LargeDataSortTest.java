package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 
 * @author wangyuyuan
 * ��һ�����ļ��е��������� �޷�һ�ζ����ڴ�����Ĵ�����
 *  ˼·���ǰ�һ�����ļ���ֳɶ��С�ļ���������ѡ����20���� Ȼ���ÿ��С�ļ��е����ݽ�������
 *  Ȼ���ٿ���һ�����ȼ�����queue �ȴ�ÿ��С�ļ���ȡһ������ Ȼ���queue����С��д��Ŀ����ļ� ͬʱ��¼��д���
 *  ���ĸ�С�ļ������� ���ҵ����С�ļ���ȡһ����������� ֪������Ϊ���������������
 */
public class LargeDataSortTest {
	static File file = new File("E:"+File.separator+"dataTest"+File.separator+"data.txt");
	static File file1 = new File("E:"+File.separator+"dataTest"+File.separator+"dataSorted.txt");
	public static void main(String[] args) throws Exception{
		createData();
		System.out.println("���ļ�д��ɹ�");
		separateFile();
		System.out.println("�ļ���ֳɹ�");
	
		everySingleFileSort();
		System.out.println("С�ļ��������");
		mergeFile();
		System.out.println("������������� ����д����һ�����ļ�");
		
	}
	public static void createData() throws IOException{
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		Random random = new Random();
		for(int i=0;i<10000000;i++){
			bw.write(random.nextInt(Integer.MAX_VALUE)+"\r\n");
		}
		bw.close();
		fw.close();
	}
	public static void separateFile() throws IOException{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		FileWriter fw = null;
		BufferedWriter bw = null;
		List<FileWriter> fwList = new LinkedList<FileWriter>();
		List<BufferedWriter> bwList = new LinkedList<BufferedWriter>();
		for(int i=0;i<20;i++){
			fw = new FileWriter("E:"+File.separator+"dataTest"+File.separator+"data"+i+".txt");
			bw = new BufferedWriter(fw);
			//�Ѷ�����뼯��
			fwList.add(fw);
			bwList.add(bw);
		}
		
		while(br.ready()){
			for(Iterator<BufferedWriter> iterator=bwList.iterator();iterator.hasNext();){
				BufferedWriter it = iterator.next();
				it.write(br.readLine()+"\r\n");
				continue;//��һ��bw���������һ���� Ȼ��д��С�ļ� ���ļ�����һ�����ݷ����һ��С�ļ� �ڶ������ݷ���ڶ���С�ļ�
			}
		}
		br.close();
		fr.close();
		//�����ر��������ļ���   
		for (Iterator iterator = bwList.iterator(); iterator.hasNext();) {
			BufferedWriter it = (BufferedWriter) iterator.next();
			it.close();
		}
		
		for (Iterator iterator = fwList.iterator(); iterator.hasNext();) {
			FileWriter it = (FileWriter) iterator.next();
			it.close();
		}
	}
	//��ÿ��С�ļ���������
	public static void everySingleFileSort() throws Exception{
		LinkedList<Integer> numbers ;
		for(int i=0;i<20;i++){
			numbers = new LinkedList<Integer>();
			String path = "E:"+File.separator+"dataTest"+File.separator+"data"+i+".txt";
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			while(br.ready()){
				numbers.add(Integer.parseInt(br.readLine()));
			}
			Collections.sort(numbers);
			numbersWrite(numbers,path);//�źú�д��С�ļ�
			br.close();
			fr.close();
		}
	}
	//���ź����û���ļ�д�ص�С�ļ���
	public static void numbersWrite(LinkedList<Integer> numbers,String path) throws IOException{
		FileWriter fw  = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);
		for(Iterator<Integer> iterator=numbers.iterator();iterator.hasNext();){
			Integer num = (Integer)iterator.next();
			bw.write(num+"\r\n");
		}
		bw.close();
		fw.close();
	}
	//�ٽ�����С�ļ����ϵ�һ�����ļ���
	public static void mergeFile() throws Exception{
		PriorityQueue<Obj> queue = new PriorityQueue<Obj>(20,new Obj());
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = new FileWriter(file1);
		BufferedWriter bw = new BufferedWriter(fw);
		List<FileReader> frList = new LinkedList<FileReader>();
		List<BufferedReader> brList = new LinkedList<BufferedReader>();
		int n;
		for(int i=0;i<20;i++){
			String path = "E:"+File.separator+"dataTest"+File.separator+"data"+i+".txt";
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			frList.add(fr);
			brList.add(br);
		}
		//��ʼ��ÿ��С�ļ��ĵ�һ������������� 
		for(int i=0;i<=20;i++){
			BufferedReader buffR;
			if(i==20){//�����е�һ������ʱ�� ��ʼ������ ͬʱͨ��obj�е�b��¼�������ĸ�С�ļ������� ��Ȼ���� �Ǿ��ٴӴ�С�ļ���ȡһ������� ֱ�����п���д�����
				while(queue.size()!=0){
					Obj obj = queue.poll();
					bw.write(obj.a+"\r\n");
					buffR = brList.get(obj.b);
					while(buffR.ready()&&queue.size()<20){
						n = Integer.parseInt(buffR.readLine());
						queue.add(new Obj(n,obj.b));
					}
				}
				break;
			}
			buffR = brList.get(i);
			while(buffR.ready()&&queue.size()<20){
				n = Integer.parseInt(buffR.readLine());
				Obj obj = new Obj(n,i);
				queue.add(obj);
				break;
			}
		}
		bw.close();
		fw.close();
		//�����ر��������ļ���   
		for (Iterator iterator = brList.iterator(); iterator.hasNext();) {
			BufferedReader it = (BufferedReader) iterator.next();
			it.close();
		}
		
		for (Iterator iterator = frList.iterator(); iterator.hasNext();) {
			FileReader it = (FileReader) iterator.next();
			it.close();
		}
	}
}
/**
 * �����������ʱ�� aΪ��С�ļ����������� b������¼�������Ǵ��ĸ�С�ļ�������
 * @author wangyuyuan
 *
 */
class Obj implements Comparator<Obj>{
	int a,b;
	Obj(){}
	Obj(int a,int b){
		this.a =a;
		this.b=b;
	}
	public int compare(Obj o1, Obj o2) {
		return o1.a-o2.a;
	}
}
