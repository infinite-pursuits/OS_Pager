
public class Frame {
	Process p;int cpagef;
	int fuse;int mru;int free;int id; //1  for free, 0 for not free

public Frame() {
	p=null;fuse=-1;mru=-1;free=1; id=-1;cpagef=-1;
}
public Frame(int id) {
	p=null;fuse = -1;mru = -1;free = 1;this.id = id;cpagef=-1;
}

public void insertpage(Process p,int cycle) {
	mru=fuse=cycle;
	this.free = 0;
	this.p = p;
	this.cpagef=p.current_page;
	(this.p.res_times).set(cpagef,cycle);
}

public void replacepage(Process p,int cycle)
{
	
	this.p.total_res_t+=(cycle-(this.p.res_times.get(cpagef)));
     (this.p.res_times).set(cpagef,0);
	this.p.ne++;
	insertpage(p,cycle);
}
}
