package bank;

        class Account {

            // Attributes

            private String name;
            private int balance;
            private int decouvert;
            private boolean block;

            // Constructor

            public void setName(String name) {
                this.name = name;
            }

            public void setBalance(int balance) { this.balance = balance; }

            public void setDecouvert(int decouvert) { this.decouvert = decouvert; }

            public void setBlock(boolean block) { this.block = block; }

            // Methods

            public String getName() { return name; }

            public int getBalance() { return balance; }

            public int getDecouvert() { return decouvert; }

            public boolean isBlock() { return block; }

            public String toString() {
                String status = "";
                status = status + this.name + " | " + this.balance + " | " + this.decouvert + " | " + this.block + "\n";
                return status;
            }
        }
