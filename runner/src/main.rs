use clap::Parser;
use std::path::PathBuf;

#[derive(Debug, Parser)]
#[clap(about, author, version)]
struct Cli {
    /// Address to reach the docker engine of the database host
    #[clap(long)]
    database_host: String,
    /// Path to the file that will be used to run the migrations
    #[clap(long)]
    migration_path: PathBuf,
    /// Address to reach the docker engine of the server host
    #[clap(long)]
    server_host: String,
    /// Path to the server that will be run
    #[clap(long)]
    server_path: PathBuf,
}

fn main() {
    let cli = Cli::parse();
    println!("cli: {:?}", cli);
}
