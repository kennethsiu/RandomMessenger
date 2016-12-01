/**
 * Created by Anu on 11/20/2016.
 */

import android.util.Log;
import static junit.framework.Assert.assertEquals;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omagle.omagle.ChatScreen;
import com.omagle.omagle.Message;
import com.omagle.omagle.MyUser;

//test class that uses unit tests to test the methods
public class test {
    private DatabaseReference myDatabase;
    private static final String TAG = "JUnit tests";
    private ChatScreen sc = new ChatScreen();
    private int testCount = 0;

    //send messages and check if it was stored properly on the database
    public void storeMessageTest() throws Exception {
        //sending out 5 messages
        while (testCount < 5) {
            if (testCount == 0)
                sc.storeMessage("Store this message to test");
            else if (testCount == 1)
                sc.storeMessage(" ");
            else if (testCount == 2)
                sc.storeMessage("HOW ABOUT THIS");
            else if (testCount == 3)
                sc.storeMessage("!@#$%");
            else if (testCount == 4)
                sc.storeMessage("   spaces  ");
            //get snapshot of the database
            ValueEventListener messageListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Message m = dataSnapshot.child("message").child(sc.newUser.getPartner()).getValue(Message.class);
                    //check if the messages were stored properly
                    if (testCount == 0 && m != null)
                        assertEquals(m.getText(), "Store this message to test");
                    else if (testCount == 1 && m != null)
                        assertEquals(m.getText(), " ");
                    else if (testCount == 2 && m != null)
                        assertEquals(m.getText(), "HOW ABOUT THIS");
                    else if (testCount == 3 && m != null)
                        assertEquals(m.getText(), "!@#$%");
                    else if (testCount == 4 && m != null)
                        assertEquals(m.getText(), "   spaces  ");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "store message test failed", databaseError.toException());
                }
            };
            DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference();
            messageDatabase.addValueEventListener(messageListener);
            testCount++;
        }
    }

    /*check that if user is matched, then the partner token is set properly
/make sure that the information is properly stored in the database
*/
    public void matchUsersTest() throws Exception {
        //get snapshot of database
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("users");
                //match user
                sc.matchUsers(users);
                for (DataSnapshot snap : users.getChildren()) {
                    MyUser potentialPartner = snap.getValue(MyUser.class);
                    //partner needs to be default partner if not matched properly
                    if (!potentialPartner.getMatched())
                        assertEquals(potentialPartner.getPartner(), "Default partner");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Match Partner Test failed", databaseError.toException());
            }
        };
        myDatabase.addListenerForSingleValueEvent(userListener);
    }

    //check if messages were received/retrieved properly from the database
    public void retrieveMessageTest() throws Exception {
        testCount = 0;
        //testing 5 cases
        while(testCount < 5) {
            //send 5 different messages
            if(testCount == 0)
                sc.storeMessage("testing first retrieve message");
            else if(testCount == 1)
                sc.storeMessage("  ");
            else if(testCount == 2)
                sc.storeMessage("DID YOU GET THIS MESSAGE??");
            else if(testCount == 3)
                sc.storeMessage("  how about this one?  ");
            else if (testCount == 4)
                sc.storeMessage("special chars !@#");
            //get snapshot of the database
            ValueEventListener messageListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String retrievedM = sc.retrieveMessage(dataSnapshot);
                    //see if the 5 messages that were sent were received properly
                    if(testCount == 0)
                        assertEquals(retrievedM, "testing first retrieve message");
                    else if(testCount == 1)
                        assertEquals(retrievedM, "  ");
                    else if(testCount == 2)
                        assertEquals(retrievedM, "DID YOU GET THIS MESSAGE??");
                    else if(testCount == 3)
                        assertEquals(retrievedM, "  how about this one?  ");
                    else if(testCount == 4)
                        assertEquals(retrievedM, "special chars !@#");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "retrieve message test failed", databaseError.toException());
                }
            };
            DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference();
            messageDatabase.addValueEventListener(messageListener);
            testCount++;
        }
    }
}
