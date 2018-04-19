import java.util.*;

public class FrameTable {
	int full;//0 not full,1 full
	Frame lru_frame;
	Frame oldest_frame;
	Frame[] frames ;

	
	public FrameTable() {
		frames = new Frame[Paging.M / Paging.pageSize];
		lru_frame = null;
		oldest_frame = null;
		full = 0;
		
		for(int i=0;i<frames.length;i++)
			frames[i]=new Frame(i);
	}
	public void inc_res(int cycle)
	{for (int i = 0; i < frames.length; i++) {
		if (frames[i].free == 0) 
		{	frames[i].p.total_res_t ++;
	}}
		
	}
	
	public Frame free_frame() {
		Frame free = null;
		for(int i= 0 ; i < frames.length; i++)
		{	
			if(frames[i].free == 1) {
				if (free == null) {
					free = frames[i];
				} else if (free.id < frames[i].id) {
					free = frames[i];
				}
 			}
		}
		return free;
	}
	
	public int hit(Process p,int cycle)
	{
		for (int i=0;i<frames.length;i++)
	{
		if(frames[i].p != null && (frames[i].p.id==p.id)&&(frames[i].cpagef==p.current_page))
		{	frames[i].mru=cycle;
			return frames[i].id;
		}
	}
		return -1;
	}
	
	public Frame find_lru_frame()
	{	
		Arrays.sort(frames,new Comparator<Frame>() {
			 @Override
			  public int compare(Frame f1, Frame f2) {
		            return f1.mru-f2.mru;
		        }
		});
		
		return frames[0];
	}
	
	public Frame fifo_frame()
	{	
		Arrays.sort(frames,new Comparator<Frame>() {
			 @Override
			  public int compare(Frame f1, Frame f2) {
		            return f1.fuse-f2.fuse;
		        }
		});
		
		return frames[0];
		
	}
	
	public Frame random_Frame(Scanner sc)
	{
		long j=sc.nextLong()%(long)frames.length;
		int i=(int) j;
		return frames[i];
	}
}
