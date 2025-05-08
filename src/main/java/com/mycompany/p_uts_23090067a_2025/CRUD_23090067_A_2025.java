package com.mycompany.p_uts_23090067a_2025;

import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class CRUD_23090067_A_2025 {

    public static void main(String[] args) {
        //Koneksi Databse mongodb
        String URL = "mongodb://localhost:27017";

        // Try-with-resources untuk memastikan MongoClient ditutup otomatis
        try (MongoClient mongoClient = MongoClients.create(URL)) {
            
            // Koneksi ke database dan koleksi
            MongoDatabase database = mongoClient.getDatabase("uts_23090067_A_2025");
            MongoCollection<Document> collection = database.getCollection("COLL_23090067_A_2025");

            // CREATE - Tambah 3 dokumen
            createDocuments(collection);

            // READ - Tampilkan semua dokumen
            System.out.println("\n--- SEMUA DATA ---");
            readDocuments(collection);

            // UPDATE - Update data dengan kondisi (contoh: nama = Pulpen)
            System.out.println("\n--- UPDATE ---");
            updateDocument(collection, "Pulpen", "warna", "Hijau");

            // READ ulang setelah update
            readDocuments(collection);

            // DELETE - Hapus data berdasarkan nama
            System.out.println("\n--- DELETE ---");
            deleteDocument(collection, "Buku");

            // READ ulang setelah delete
            readDocuments(collection);

            // SEARCH - Cari dokumen berdasarkan key
            System.out.println("\n--- SEARCH KEY 'warna' ---");
            searchDocumentByKey(collection, "warna");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CREATE
    static void createDocuments(MongoCollection<Document> collection) {
        Document doc1 = new Document("nama", "Buku").append("harga", 20000);
        Document doc2 = new Document("nama", "Pulpen").append("harga", 5000).append("warna", "Biru");
        Document doc3 = new Document("nama", "Tas").append("harga", 150000).append("warna", "Hitam").append("stok", 10);
        
        collection.insertMany(java.util.List.of(doc1, doc2, doc3));
        System.out.println("3 dokumen berhasil ditambahkan.");
    }

    // READ
    static void readDocuments(MongoCollection<Document> collection) {
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }
    }

    // UPDATE
    static void updateDocument(MongoCollection<Document> collection, String namaProduk, String key, Object newValue) {
        Document update = new Document("$set", new Document(key, newValue));
        collection.updateOne(eq("nama", namaProduk), update);
        System.out.println("Dokumen dengan nama '" + namaProduk + "' berhasil diupdate.");
    }

    // DELETE
    static void deleteDocument(MongoCollection<Document> collection, String namaProduk) {
        collection.deleteOne(eq("nama", namaProduk));
        System.out.println("Dokumen dengan nama '" + namaProduk + "' berhasil dihapus.");
    }

    // SEARCH
    static void searchDocumentByKey(MongoCollection<Document> collection, String key) {
        FindIterable<Document> docs = collection.find(exists(key));
        boolean found = false;
        for (Document doc : docs) {
            System.out.println("Dokumen ditemukan: " + doc.toJson());
            found = true;
        }
        if (!found) {
            System.out.println("Tidak ada dokumen dengan key: " + key);
        }
    }
}
