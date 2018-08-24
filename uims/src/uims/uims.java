package uims;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;

public class uims {
	static Connection conn=null;
	static Scanner sc=new Scanner(System.in);
	static Statement stmt = null;
	public static Connection getDbConnection()
	{
		
		
		try 
		{
			String driverClassName = "org.postgresql.Driver";
			Class.forName(driverClassName);
			String connectionUrl = "jdbc:postgresql://localhost:5432/uims";
			conn=DriverManager.getConnection(connectionUrl, "postgres", "asdfghjkl78692");
			
			if(conn!=null)
				System.out.println("Connection With Database");
			else 
				System.out.println("Connection Failed");
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void main(String args[])
	{
		conn = getDbConnection();
		if(conn!=null) 
		{
			int i,i2;
			System.out.println("1. for Login");
			System.out.println("2. for Registration");
			System.out.println("0. for exit");
			System.out.println("Enter Your Choice : ");
			i2=sc.nextInt();
			do
			{
				switch(i2)
				{
					case 1:
						do
						{
							System.out.println("1. Login for Admin");
							System.out.println("2. Login for Student");
							System.out.println("3. Login for Recruiter");
							System.out.println("0. for Exit");
							i=0;
							i=sc.nextInt();
							switch(i)
							{
								case 1 :
									admin_login();	
									break;
								case 2 :
									student_login();
									break;
								case 3:
									recruiter_login();
								default :
									System.out.println("wrong choice");
									break;
							}
						}while(i!=0);
						break;
					case 2:
						String fname=null,lname=null,uname=null,pwd=null,address=null,city=null,email=null,mobile=null,query=null;
						int cid = 0,year = 0,sid = 0;
						do
						{
							System.out.println("1. Register for Student");
							System.out.println("2. Register for Recruiter");
							System.out.println("0. for Exit");
							i=0;
							i=sc.nextInt();
							switch(i)
							{
								case 1 :
									System.out.println("Enter First name : ");
									fname=sc.next();
									System.out.println("Enter Last name : ");
									lname=sc.next();
									System.out.println("Enter Course id :");
									cid=sc.nextInt();
									System.out.println("Enter Username :");
									uname=sc.next();
									System.out.println("Enter Password : ");
									pwd=sc.next();
									System.out.println("Enter Year(Admitted) :  ");
									year=sc.nextInt();
									System.out.println("Enter Address : ");
									address=sc.next();
									System.out.println("Enter City : ");
									city=sc.next();
									System.out.println("Enter Email :");
									email=sc.next();
									System.out.println("Enter Contact no : ");
									mobile=sc.next();
									
										try
										{
											stmt = conn.createStatement();
											ResultSet rs= stmt.executeQuery("select count(*) from uims.student");
											if(rs.next())
												sid=rs.getInt(1)+1;
										}
										catch(Exception e)
										{
											e.printStackTrace();
											return;
										}
									query="insert into uims.student values ("+sid+",'"+fname+"','"+lname+"',"+cid+",'"+uname+"','"+pwd+"',"+year+",'"+address+"','"+city+"','"+email+"','"+mobile+"')";
									a_query(query);
									break;
								case 2 :
									System.out.println("Enter name : ");
									fname=sc.next();
									System.out.println("Enter Username :");
									uname=sc.next();
									System.out.println("Enter Password : ");
									pwd=sc.next();
									System.out.println("Enter Address : ");
									address=sc.next();
									System.out.println("Enter City : ");
									city=sc.next();
									System.out.println("Enter Email :");
									email=sc.next();
									System.out.println("Enter Contact no : ");
									mobile=sc.next();
										try
										{
											stmt = conn.createStatement();
											ResultSet rs= stmt.executeQuery("select count(*) from uims.recruiter");
											int rid;
											if(rs.next())
												rid=rs.getInt(1)+1;
										}
										catch(Exception e)
										{
											e.printStackTrace();
											return;
										}		
									query="insert into uims.recruiter values ("+sid+",'"+fname+"','"+uname+"','"+pwd+"','"+city+"','"+address+"','"+email+"','"+mobile+"');";
									a_query(query);
									break;
								default :
									System.out.println("wrong choice");
									break;
							}
						}while(i!=0);
						break;
					default :
						System.out.println("wrong choice");
						break;
				}
			}while(i2!=0);
		}
	}
	
	private static void admin_login() 
	{
		
		try {
			String user="";
			String pwd="";
			
			System.out.println("Enter Username : ");
			user=sc.next();
			System.out.println("Enter Password : ");
			pwd=sc.next();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM uims.admin where username='"+ user +"' and password='"+ pwd +"';");
			if(rs.next())
			{
				admin_panel();
			}
			else
			{
				System.out.println("Password mismatch");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void student_login() 
	{
		
		try {
			String user="";
			String pwd="";
			
			System.out.println("Enter Username : ");
			user=sc.next();
			System.out.println("Enter Password : ");
			pwd=sc.next();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM uims.student where username='"+ user +"' and password='"+ pwd +"';");
			if(rs.next())
			{
				int id=rs.getInt(1);
				student_panel(id);
			}
			else
			{
				System.out.println("Password mismatch");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}

	private static void recruiter_login() 
	{		
		try 
		{
			String user=null;
			String pwd=null;
			
			System.out.println("Enter Username : ");
			user=sc.next();
			System.out.println("Enter Password : ");
			pwd=sc.next();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM uims.recruiter where username='"+ user +"' and password='"+ pwd +"';");
			if(rs.next())
			{
				recruiter_panel(rs.getInt("rid"));
			}
			else
			{
				System.out.println("Password mismatch");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void admin_panel() {
		// TODO Auto-generated method stub
		int i=1;
		do
		{
			String course=null,rectr=null,query=null,header=null;
			int year;
			
			System.out.println("1.  To view students who are recruited");
			System.out.println("2.  To view students who are not recruited");
			System.out.println("3.  To view students who are in specific course and recruited");
			System.out.println("4.  To view students who are in specific course and not recruited");
			System.out.println("5.  To view students who are in specific course admitted in specific year and recruited");
			System.out.println("6.  To view students who are in specific course admitted in specific year and not recruited");
			System.out.println("7.  To view number of students who are recruited by recruiters");
			System.out.println("8.  To view stipend given by specific recruiter");
			System.out.println("9.  To view stipend given by recruiters in ascending order");
			System.out.println("10. To find number of student recruited by recruiters");
			System.out.println("11. To find average stipend of students studies in specific course and year");
			System.out.println("12. To delete student ");
			System.out.println("13. To delete recruiter");
			System.out.println("0.  For LOGOUT");
			System.out.println("Enter the choice : ");
			i=sc.nextInt();
			switch(i)
			{
				case 1 :
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city,r.rname as recruiter from uims.student s inner join uims.course c on c.cid=s.cid inner join uims.recruitements rs  on s.sid=rs.sid inner join uims.recruiter r on r.rid=rs.rid";
					a_query(query,7);
					break;
				case 2:
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city from uims.student s inner join uims.course c on c.cid=s.cid where NOT exists((select s.sid from uims.student inner join uims.recruitements rs on s.sid=rs.sid))";
					a_query(query,6);
					break;
				case 3:
					System.out.println("Enter Course Name : ");
					course=sc.next();
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city,r.rname as recruiter from uims.student s inner join uims.course c on s.cid=c.cid inner join uims.recruitements rs on s.sid=rs.sid inner join uims.recruiter r on r.rid=rs.rid where c.cid=(select cs.cid from uims.course cs where cs.cname='"+course+"')";
					a_query(query,7);
					break;
				case 4:
					System.out.println("Enter Course Name : ");
					course=sc.next();
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city from uims.student s inner join uims.course c on c.cid=s.cid where c.cid=(select c.cid from uims.course c where c.cname='"+course+"') and NOT exists((select s.sid from uims.student inner join uims.recruitements rs  on s.sid=rs.sid))";
					a_query(query,3);
					break;
				case 5:
					System.out.println("Enter Course Name : ");
					course=sc.next();
					System.out.println("Enter Year : ");
					year=sc.nextInt();
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city,r.rname as recruiter from uims.student s inner join uims.course c on s.cid=c.cid inner join uims.recruitements rs on s.sid=rs.sid inner join uims.recruiter r on r.rid=rs.rid where c.cid=(select cs.cid from uims.course cs where cs.cname='"+course+"') and s.year="+year+" ";
					a_query(query,4);
					break;
				case 6:
					System.out.println("Enter Course Name : ");
					course=sc.next();
					System.out.println("Enter Year : ");
					year=sc.nextInt();
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city from uims.student s inner join uims.course c on c.cid=s.cid where s.cid=(select c.cid from uims.course c where c.cname='"+course+"') and s.year="+year+" and exists(SELECT s.sid FROM uims.student s WHERE NOT EXISTS (select rs.sid from uims.recruitements rs where rs.sid=s.sid))";
					a_query(query,7);
					break;
				case 7:
					query="select count(rs.rid),r.rname from uims.recruitements rs inner join uims.recruiter r on rs.rid=r.rid group by r.rname";
					a_query(query,2);
					break;
				case 8:
					System.out.println("Enter Recruiter Name : ");
					rectr=sc.next();
					query="select distinct rs.stipend,r.rname from uims.recruitements rs inner join uims.recruiter r on rs.rid=r.rid  where r.rid=(select r.rid from uims.recruiter r where r.rname='"+rectr+"')";
					a_query(query,2);
					break;
				case 9:
					query="select distinct rs.stipend,r.rname from uims.recruitements rs inner join uims.recruiter r on rs.rid=r.rid  where exists(select r.rid from uims.recruiter r) order by rs.stipend";
					a_query(query,2);
					break;
				case 10:
					query="select count(rs.rid),r.rname from uims.recruitements rs inner join uims.recruiter r on rs.rid=r.rid group by r.rname";
					a_query(query,2);
					break;
				case 11:
					System.out.println("Enter Course Name : ");
					course=sc.next();
					System.out.println("Enter Year : ");
					year=sc.nextInt();
					query="select avg(rs.stipend) from uims.recruitements rs inner join uims.student s on rs.sid=s.sid where exists(select s.sid from uims.student s where s.cid=(select s.cid from uims.course c where c.cname='"+course+"')) and s.year="+year+" ";
					a_query(query,1);
					break;
				case 0:
					break;
				default :
					System.out.println("Wrong Choice");
			}
		}while(i!=0);
	}

	private static void student_panel(int id) 
	{
		int i=1,i2=1,month,cid,year;
		String query=null,tmp_str=null,title=null;
		do
		{
			
			System.out.println("1. To update details");
			System.out.println("2. To add projects");
			System.out.println("3. To get cv generating link");
			System.out.println("4. To apply for internship");
			System.out.println("5. To view stipend given by specific recruiter");
			System.out.println("6. To view stipend given by recruiters in ascending order");
			System.out.println("7. To view number of students who are recruited by recruiters");
			System.out.println("8. To find average stipend of students studies in specific course and year");
			System.out.println("0. For LOGOUT");
			System.out.println("Enter your choice : ");
			i=sc.nextInt();
			switch(i)
			{
				case 1:
					do
					{
						System.out.println("1. for first name");
						System.out.println("2. for last name");
						System.out.println("3. for username");
						System.out.println("4. for password");
						System.out.println("5. for address");
						System.out.println("6. for city");
						System.out.println("7. for email");
						System.out.println("8. for mobile no");
						System.out.println("0. For stop updating details");
						System.out.println("Enter your choice : ");
						i2=sc.nextInt();
						switch(i2)
						{
							case 1:
								System.out.println("Enter first name : ");
								tmp_str=sc.next();
								query="update  uims.student s set fname='"+tmp_str+"' where s.sid="+id+" ";
								a_query(query,"name updated successfully");
								break;
							case 2:
								System.out.println("Enter last name : ");
								tmp_str=sc.next();
								query="update  uims.student s set lname='"+tmp_str+"' where s.sid="+id+" ";
								a_query(query);
								break;
							case 3:
								System.out.println("Enter username : ");
								tmp_str=sc.next();
								query="update  uims.student s set username='"+tmp_str+"' where s.sid="+id+"";
								a_query(query);
								break;
							case 4:
								System.out.println("Enter password : ");
								tmp_str=sc.next();
								query="update  uims.student s set password='"+tmp_str+"' where s.sid="+id+" ";
								a_query(query);
								break;
							case 5:
								System.out.println("Enter address : ");
								tmp_str=sc.next();
								query="update  uims.student s set address='"+tmp_str+"' where s.sid="+id+" ";
								a_query(query);
								break;
							case 6:
								System.out.println("Enter city : ");
								tmp_str=sc.next();
								query="update  uims.student s set city='"+tmp_str+"' where s.sid="+id+" ";
								a_query(query);
								break;
							case 7:
								System.out.println("Enter email : ");
								tmp_str=sc.next();
								query="update  uims.student s set email='"+tmp_str+"' where s.sid="+id+" ";
								a_query(query);
								break;
							case 8:
								System.out.println("8. Enter mobile no : ");
								tmp_str=sc.next();
								query="update  uims.student s set mobile_no='"+tmp_str+"' where s.sid="+id+" ";
								a_query(query);
								break;
						}
					}while(i2!=0);
					break;
				case 2:
					title=sc.next();
					month=sc.nextInt();
					tmp_str=sc.next();
					query="insert into uims.project(sid,title,duration,guide) values ("+id+",'"+title+"',"+month+",'"+tmp_str+"')";
					a_query(query,"Project Inserted Successfully");
					break;
				case 3:
					System.out.println("http://instaresume.kunalvarma.in");
					break;
				case 4:
					try
					{
						stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery("select sid from uims.recruitements rs where rs.sid="+id+" ");
						if(rs.next())
						{
							System.out.println("Sorry, You Have Already Recruited");
							break;
						}
						else
						{
							query="insert into uims.application (sid,link) values ("+id+",'https://drive.google.com/open?id=1Nit2T9UGeoQKOgBTT13nkXeJnGN5wa-iUslpCkQZxzU')";
							a_query(query,"Congratulations, You have applied for internship");
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				case 5:
					System.out.println("Enter Recruiter Name : ");
					tmp_str=sc.next();
					query="select distinct rs.stipend,r.rname from uims.recruitements rs inner join uims.recruiter r on rs.rid=r.rid  where r.rid=(select r.rid from uims.recruiter r where r.rname='"+tmp_str+"')";
					a_query(query,2);
					break;
				case 6:
					query="select distinct rs.stipend,r.rname from uims.recruitements rs inner join uims.recruiter r on rs.rid=r.rid  where exists(select r.rid from uims.recruiter r) order by rs.stipend";
					a_query(query,2);
					break;
				case 7:
					query="select count(rs.rid),r.rname from uims.recruitements rs inner join uims.recruiter r on rs.rid=r.rid group by r.rname";
					a_query(query,2);
					break;
				case 8:
					System.out.println("Enter year : ");
					year=sc.nextInt();
					System.out.println("Enter course id : ");
					cid=sc.nextInt();
					query="select avg(rs.stipend) from uims.recruitements rs inner join uims.student s on s.sid=rs.sid inner join uims.course c on c.cid=s.cid where s.year="+year+" and s.cid="+cid+" ";
					a_query(query,1);
					break;
				case 0:
					break;
				default :
					System.out.println("Wrong Choice");
			}
		}while(i!=0);
	}
	
	private static void recruiter_panel(int rid) 
	{
		int i=1,sid,stipend,month;
		String query=null,date=null;
		do
		{
			System.out.println("1. To view students details recruited by us");
			System.out.println("2. To view students who are applied for internship");
			System.out.println("3. To view sepcific student recruited by us");
			System.out.println("4. To find total recruited students by us");
			System.out.println("5. To find project done by specific student");
			System.out.println("6. To recruit student for internship");
			System.out.println("0. For LOGOUT");
			i=sc.nextInt();
			switch(i)
			{
				case 1:
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city,r.rname as recruiter from uims.student s inner join uims.course c on s.cid=c.cid inner join uims.recruitements rs  on s.sid=rs.sid inner join uims.recruiter r on r.rid=rs.rid where r.rid="+rid+" ";
					a_query(query,8);
					break;
				case 2:
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city from uims.student s inner join uims.course c on s.cid=c.cid inner join uims.application a on a.sid=s.sid";
					a_query(query,7);
					break;
				case 3:
					query="select s.sid,s.fname as firstname,s.lname as lastname,c.cname,s.email,s.mobile_no,s.city from uims.student s inner join uims.course c on c.cid=s.cid inner join uims.recruitements rs on rs.sid=s.sid where rs.rid="+rid+" ";
					a_query(query,7);
					break;
				case 4:
					query="select count(s.sid) from uims.student s inner join uims.recruitements rs on rs.sid=s.sid where rs.rid= "+rid+" ";
					a_query(query,"Students are recruited by us");
					break;
				case 5:
					System.out.println("Enter student Id : ");
					sid=sc.nextInt();
					query="select p.title,p.duration,p.guide from uims.project p where p.sid="+sid+" ";
					a_query(query);
					break;
				case 6:
					System.out.println("Enter student Id : ");
					sid=sc.nextInt();
					System.out.println("Enter Date(yyyy-mm-dd) : ");
					date=sc.next();
					System.out.println("Enter Duration(no of months) : ");
					month=sc.nextInt();
					System.out.println("Enter Stipend : ");
					stipend=sc.nextInt();
					try
					{
						stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery("select sid from uims.recruitements rs where rs.sid="+sid+" ");
						if(rs.next())
						{
							System.out.println("Sorry, This Student is Already Recruited");
							break;
						}
						else
						{
							query="insert into uims.recruitements values ("+rid+","+sid+",'"+date+"',"+month+","+stipend+")";
							int no=a_query1(query,"Congratulations, You have Recruited the Student");
							if(no==1)
							{
								query="delete from uims.application a where a.sid="+sid+" ";
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				case 0:
					break;
				default :
					System.out.println("Wrong Choice");
			}
		}while(i!=0);
	}
	
	private static void a_query(String qry,int len) 
	{
		try
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next())
			{	
				for(int i=1;i<=len;i++)
				{
					System.out.print(rs.getString(i));
					System.out.print("  ");
				}
				System.out.println();
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	private static void a_query(String qry) 
	{
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(qry);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		
	}
	
	private static void a_query(String qry,String str) 
	{
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(qry);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		System.out.println(str);
		
	}
	private static int a_query1(String qry,String str) 
	{
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(qry);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		System.out.println(str);
		return 1;
		
	}
}