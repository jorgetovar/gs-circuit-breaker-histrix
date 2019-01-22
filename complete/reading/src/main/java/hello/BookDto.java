package hello;

public class BookDto {
	
	public BookDto() {
		super();
	}

	private String title;

	public BookDto(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
