package cseb.tech.smarthublms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cseb.tech.smarthublms.Adapters.PostAdapter;

public class Feed_Fragment extends Fragment {

    private EditText inputPost;
    private Button btnSubmit;
    private RecyclerView recyclerViewFeed;
    private Button btnViewImage;
    private ImageView imageViewSelected;
    private Uri selectedImageUri;

    // Firebase
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private FirebaseDatabase database;

    private FirebaseStorage storage;
    private List<Post> postsList = new ArrayList<>();
    private PostAdapter adapter;

    public Feed_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_activity, container, false);

        inputPost = view.findViewById(R.id.inputPost);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        imageViewSelected = view.findViewById(R.id.imageViewSelected);
        btnViewImage = view.findViewById(R.id.btnChooseImage);
        recyclerViewFeed = view.findViewById(R.id.recyclerViewFeed);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        FirebaseApp.initializeApp(getContext());
        // Set up RecyclerView
        adapter = new PostAdapter(postsList);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFeed.setAdapter(adapter);

        btnViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postContent = inputPost.getText().toString();
                if (!postContent.isEmpty()) {
                    if (selectedImageUri != null) {
                        uploadImageToFirebaseAndSavePost(selectedImageUri, postContent);
                    } else {
                        fetchUserNameAndSavePost(postContent, null);
                    }
                }
            }
        });

        fetchPostsFromFirestore();
        return view;
    }

    private void fetchUserNameAndSavePost(String content, @Nullable String imageUrl) {
        if (mAuth.getCurrentUser() != null) {
            String currentUserUID = mAuth.getCurrentUser().getUid();
            db.collection("Student").document(currentUserUID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String userName = documentSnapshot.getString("Name");
                        if (userName != null) {
                            savePostToFirestore(content, userName, imageUrl);
                        } else {
                            Toast.makeText(getContext(), "Error: Name not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Error: User document doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error fetching user name", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePostToFirestore(String content, String userName, @Nullable String imageUrl) {
        Map<String, Object> post = new HashMap<>();
        post.put("content", content);
        post.put("postedBy", userName);
        post.put("timestamp", new Date());
        if (imageUrl != null) {
            post.put("imageUrl", imageUrl);
        }

        db.collection("posts").add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Post added successfully!", Toast.LENGTH_SHORT).show();
                fetchPostsFromFirestore();
                inputPost.setText("");
                imageViewSelected.setImageDrawable(null);
                selectedImageUri = null;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error adding post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPostsFromFirestore() {
        db.collection("posts").orderBy("timestamp", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getContext(), "Error fetching posts", Toast.LENGTH_SHORT).show();
                    return;
                }

                postsList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Post post = doc.toObject(Post.class);
                    postsList.add(post);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1); // 1 is a request code for identification
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageViewSelected.setImageURI(selectedImageUri);
        }
    }

    private void uploadImageToFirebaseAndSavePost(Uri imageUri, String postContent) {
        StorageReference storageRef =storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + imageUri.getLastPathSegment());
        String test="test";
        Log.d(test, "uploadImageToFirebaseAndSavePost: runs till here");

        imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                fetchUserNameAndSavePost(postContent, uri.toString());
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Failed to Upload Image", Toast.LENGTH_SHORT).show();
        });
    }
}
