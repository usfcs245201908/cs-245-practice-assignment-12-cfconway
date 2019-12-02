import java.util.ArrayList;
import java.lang.*;
public class Hashtable
{
	ArrayList<HashNode> bucket;
	public final double LOAD_THRESHOLD = 0.5;
	int entries;
	int size;
	public Hashtable()
	{
		bucket = new ArrayList<>();
		entries=10;
		size=0;
		for(int i=0; i<entries; i++)
			bucket.add(null);

	}
	public String get(String key)
	{
		int index = getHash(key);
		HashNode head = bucket.get(index);
		while(head!= null)
		{
			if(head.key.equals(key))
				return head.value;
			else
				head = head.next; //going next next next
		}
		return null;
	}
	public int size()
	{
		return size;
	}
	public boolean containsKey(String key)
	{
		int index = getHash(key);
		HashNode head = bucket.get(index);
		while(head!= null)
		{
			if(head.key==key)
				return true;
			else
				head = head.next; //going next next next
		}
		return false;

	}
	public void put(String key, String value)
	{
		int index = getHash(key);
		HashNode head = bucket.get(index);

		while(head != null)
		{
			if(head.key.equals(key))
			{
				head.value = value;
				return;
			}
			head = head.next;

		}
		size++;
		head = bucket.get(index);
		HashNode node = new HashNode(key, value);
		node.next = head;
		bucket.set(index, node);
		if((1.0*size)/entries >= 0.5)
		{
			ArrayList<HashNode> newBucket = bucket;
			bucket = new ArrayList<>();
			entries= 2*entries;	
			size=0;
			for(int i=0; i<entries; i++)
			{
				bucket.add(null); 
			}
			for(HashNode newNode : newBucket)
			{
				while(newNode!=null)
				{
					put(newNode.key, newNode.value);
					newNode = newNode.next;
				}
			}
	
		}
	}

	public String remove(String key)throws Exception
	{ //removing head is a special case
		int index = getHash(key);
		HashNode head = bucket.get(index);
		HashNode prev = null;
		//check if its null
		while(head != null)
		{
			if(head.key.equals(key))
			{	
				break;
			}
			else
			{
				prev = head;
				head = head.next;
			}
		}
		if(head == null)
			throw new Exception("key is not in hashtable");
		size--;
		if(prev!=null)
			prev.next = head.next;
		else
			bucket.set(index, head.next);
		return head.value;						
	}
	//use hashfunction in java 
	public int getHash(String key)
	{
		int hashCode = key.hashCode();
		int i = hashCode % entries;
		return Math.abs(i);
	}
}