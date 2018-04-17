
public interface Office {
	Desk arrive();
	void leave(Desk desk);
	Desk service();
	void cleaned(Desk desk);

}
