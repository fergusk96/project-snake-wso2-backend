function seed(dbName, user, password) {
  db = db.getSiblingDB(dbName);
  db.createUser({
    user: user,
    pwd: password,
    roles: [{ role: "readWrite", db: dbName }],
  });
}

seed("dev-db", "dev-db-user", "changeit");
seed("test-db", "test-db-user", "changeit");
