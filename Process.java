import java.util.*;
public class Process {
	double avg_res=0;
	int id,current_page,current_word,nf,ne,ref,total_res_t, current_res_t,current_res_begin,current_res_end;
	ArrayList<Integer> res_times;
	public Process() {
		avg_res = 0;
		
		
		current_page = -1;
		current_word= -1;
		id = -1;
		ref = -1;
		nf = 0;
		ne = 0;
		total_res_t = 0;
		current_res_t = 0;
		current_res_begin=0;
		current_res_end=0;
		res_times=new ArrayList<>();
		for(int i=0;i<Paging.processSize/Paging.pageSize;i++)
		{
			res_times.add(0);
		}
		
	}
	public Process(int id) {
		this.id=id;
		ref=Paging.N;
		current_word=(111*id) % Paging.processSize;
		current_page=current_word/Paging.pageSize;
		res_times=new ArrayList<>();
		for(int i=0;i<Paging.processSize/Paging.pageSize;i++)
		{
			res_times.add(0);
		}
	}

	public void get_next_word(Scanner sc,int cycle)
	{
		double A=0,B=0,C=0;
		int i=Paging.J;
		int prs=Paging.processSize;
		
		switch(i)
		{	case 1: A=1; B=0; C=0; break;
			case 2: A=1; B=0; C=0; break;
			case 3: A=0; B=0; C=0; break;
			case 4: if(id==1)
				{A=0.75; B=0.25; C=0;}
					if(id==2) 
						{A=0.75; B=0; C=0.25;}
					if(id==3) 
						{A=.75; B=.125; C=.125;}
					if(id==4)
						{A=.5; B=.125; C=.125;} break;
			default: System.out.println("in default"); break;
		}
		
		long r=sc.nextLong();
		double y=(double)r/((double)(Integer.MAX_VALUE) + 1d);
		
		if(y<A) 
		{
			current_word=(current_word+1)%prs;
			}
		else
			if(y<(A+B))
			{
			current_word=(current_word-5+prs)%prs;
			}
		else
			if(y<(A+B+C)) 
			{
			current_word=(current_word+4)%prs;
			}
		else {
			
			current_word=(int)(sc.nextLong()%(long)prs);
		}
		
		current_page=current_word/Paging.pageSize;
		
	}
}
