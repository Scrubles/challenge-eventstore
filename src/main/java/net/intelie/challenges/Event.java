package net.intelie.challenges;

import java.util.UUID;

/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event {
	
	/*
	 * Added to act as key
	 */
	private final String id = UUID.randomUUID().toString();
    private final String type;
    private final long timestamp;

    public Event(String type, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }
    
    /*
     * Created for testing purposes
     */
    public Event(String type) {
    	this(type, System.currentTimeMillis());
    }
    
    public String id() {
    	return id;
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
