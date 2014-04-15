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
 * 将一个大文件中的数据排序 无法一次读入内存情况的处理方法
 *  思路就是把一个大文件拆分成多个小文件（这里我选择拆成20个） 然后把每个小文件中的数据进行排序
 *  然后再开辟一个优先级队列queue 先从每个小文件读取一个数据 然后把queue中最小的写入目标大文件 同时记录刚写入的
 *  是哪个小文件的数据 再找到这个小文件读取一个数据入队列 知道队列为空则所有排序完成
 */
public class LargeDataSortTest {
	static File file = new File("E:"+File.separator+"dataTest"+File.separator+"data.txt");
	static File file1 = new File("E:"+File.separator+"dataTest"+File.separator+"dataSorted.txt");
	public static void main(String[] args) throws Exception{
		createData();
		System.out.println("大文件写入成功");
		separateFile();
		System.out.println("文件拆分成功");
	
		everySingleFileSort();
		System.out.println("小文件排序完成");
		mergeFile();
		System.out.println("所有排序都已完成 重新写入了一个大文件");
		
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
			//把对象放入集合
			fwList.add(fw);
			bwList.add(bw);
		}
		
		while(br.ready()){
			for(Iterator<BufferedWriter> iterator=bwList.iterator();iterator.hasNext();){
				BufferedWriter it = iterator.next();
				it.write(br.readLine()+"\r\n");
				continue;//第一个bw读完后让下一个读 然后写入小文件 大文件读第一个数据放入第一个小文件 第二个数据放入第二个小文件
			}
		}
		br.close();
		fr.close();
		//遍历关闭所有子文件流   
		for (Iterator iterator = bwList.iterator(); iterator.hasNext();) {
			BufferedWriter it = (BufferedWriter) iterator.next();
			it.close();
		}
		
		for (Iterator iterator = fwList.iterator(); iterator.hasNext();) {
			FileWriter it = (FileWriter) iterator.next();
			it.close();
		}
	}
	//对每个小文件进行排序
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
			numbersWrite(numbers,path);//排好后写入小文件
			br.close();
			fr.close();
		}
	}
	//将排好序的没个文件写回到小文件中
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
	//再将所有小文件整合到一个大文件中
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
		//初始把每个小文件的第一个数读入队列中 
		for(int i=0;i<=20;i++){
			BufferedReader buffR;
			if(i==20){//当队列第一次满的时候 开始出队列 同时通过obj中的b记录出的是哪个小文件的数据 既然出了 那就再从此小文件读取一个入队列 直到队列空则写入完成
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
		//遍历关闭所有子文件流   
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
 * 用来放入队列时候 a为从小文件读出的数据 b用来记录此数据是从哪个小文件读出的
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
