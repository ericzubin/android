package com.embeddedlapps.subastas_cliente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_id = "_id";
    static final String KEY_marca = "marca";
    static final String KEY_modelo = "modelo";
    static final String KEY_año_i = "año_i";
    static final String KEY_año_f = "año_f";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "subastas";
    static final String DATABASE_TABLE_Marcas = "vehiculos_marcas";
    static final String DATABASE_TABLE_Modelos = "vehiculos_modelos";
    static final String DATABASE_TABLE_Años = "vehiculos_años";
    static final int DATABASE_VERSION = 13;
    
    static final String DATABASE_CREATE_Marcas =
        "create table vehiculos_marcas (_id integer primary key autoincrement, "+ "marca text not null);";
    static final String DATABASE_CREATE_Modelos =
            "create table vehiculos_modelos (_id integer primary key autoincrement, "+ "marca text not null, modelo text not null);";
    
    
    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d("bd","datahelper");
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
        	Log.d("bd","oncreate");
            try {
                db.execSQL(DATABASE_CREATE_Marcas);
                db.execSQL(DATABASE_CREATE_Modelos);
                
                ContentValues initialValues = new ContentValues();
                String marcas[]={"Acura","Alfa Romeo","Aston Martin", "Audi", "BMW", "Buick","Cadillac","Chevrolet","Chrysler",
                		"Dodge", "FAW","Ferrari","Fiat","Ford","GMC","Honda","Hummer","Hyundai","Infiniti", "Isuzu","Jaguar","Jeep",
                		"Lamborghini", "Land Rover", "Lincoln", "MG","Mazda","Mercedes Benz", "Mercury","Mini","mitsubishi","nissan",
                		"Peugeot", "Pontiac","Porsche","Renault","Saab","Seat","Smart","Subaru","Suzuki", "Toyota","VolksWagen","Volvo",
                		"Otras marcas"};
                
                int i=0;
                for(i=0;i<marcas.length;i++){
                initialValues.put(KEY_marca, marcas[i]);
                db.insert(DATABASE_TABLE_Marcas, null, initialValues);
                }
                
                initialValues = new ContentValues();
                
                String[] acura={"RDX","MDX","RL","TL","TSX","ZDX","Otros"};
                for(i=0;i<acura.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[0]);
                	initialValues.put(KEY_modelo, acura[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                Log.d("bd","oncreatekhjk: "+marcas[0]);
                
                String[] alfaRomeo={"147","157","GT","Otros"};
                for(i=0;i<alfaRomeo.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[1]);
                	initialValues.put(KEY_modelo, alfaRomeo[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                Log.d("bd","oncreatekhjk1");
                String[] Aston_Martin={"DB9","V8 Vantage","Otros"};
                for(i=0;i<Aston_Martin.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[2]);
                	initialValues.put(KEY_modelo, Aston_Martin[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                Log.d("bd","oncreatekhjk2");
                String[] Audi={"A1","A3","A4","A5","A6","A8","Allroad","Q3","Q5","Q7","R8","RS4","RS5","RS6","S3","S4","S5","S6","S8","TT","TTS","Otros"};
                for(i=0;i<Audi.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[3]);
                	initialValues.put(KEY_modelo, Audi[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                Log.d("bd","oncreatekhjk3");
                String[] BMW={"M3","M5","M6","Serie 1","Serie 3","Serie 5","Serie 6","Serie 7","Serie 8","X1","X3","X5","X5 W","X6","Z3","Z4","Otros"};
                for(i=0;i<BMW.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[4]);
                	initialValues.put(KEY_modelo, BMW[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                Log.d("bd","oncreatekhjk4");

                String[] Buick={"Enclave","LaCrosse","Otros"};
                for(i=0;i<Buick.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[5]);
                	initialValues.put(KEY_modelo, Buick[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                
                String[] Cadillac={"BLS" ,"Catera"," CTS","Deville","Eldorado Brougham","Escalade","Escalade ESV","Escalade EXT"," Seville"," SRX"," STS","Otros"};
                for(i=0;i<Cadillac.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[6]);
                	initialValues.put(KEY_modelo, Cadillac[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }


                String[] Chevrolet={"3500","Apache","Astra","Astro Safari","Astro Van","Avalanche","Aveo","Beretta","Blazer","C-15","C-20","Camaro","Camaro Hot Wheels","Camaro ZLI","Captiva","Cavalier","Century","Chevelle","Chevette","Chevy","Chevy Pick Up","Chevy Van","Cheyenne","Colorado","Corsa","Corvette","Cruze","Cutlass","Epica","Equinox","Express Van","Geo","HHR","Impala","Kodiak","Lumina","LUV","Malibu","Matiz","Meriva","Monte Carlo","Monza","Optra","Pick-Up","S10","Saturn","Savana","Sierra","Silhouette","Silverado","Sonic","Sonora","Spark","Suburban","Tahoe","Tigra","Tornado","Tracker","TrailBlazer","Traverse","Trax","Uplander","Vanette","Vectra","Venture",
                		"Zafira","Otros"};
                for(i=0;i<Chevrolet.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[7]);
                	initialValues.put(KEY_modelo, Chevrolet[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }


                String[] Chrysler={"300 SRT","300C","300M ","Aspen","Atos","Caravan","Cirrus","Concorde","Crossfire","Dart","Grand Caravan","Grand voyager","Intrepid","Le Baron","Magnum","Newyorker","Pacifica","Phantom","PT Cruiser","Sebring","Shadow","Spirit","Town & Country ","Voyager","Otros"};
                for(i=0;i<Chrysler.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[8]);
                	initialValues.put(KEY_modelo, Chrysler[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                String[] Dodge={"Atos","Attitude","Avenger","Caliber ","Caravan","Challenger","Charger","Dakota","Dart","Durango","Grand Caravan","H100","i10","Intrepid","Journey","Neon","Nitro","Pick-Up","Power Wagon","Ram","Stratus","Verna","Viper","Otros"};
                for(i=0;i<Dodge.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[9]);
                	initialValues.put(KEY_modelo, Dodge[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Faw={"F1","F4","F5","Otros"};
                for(i=0;i<Faw.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[10]);
                	initialValues.put(KEY_modelo, Faw[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                String[] Ferrari={"360","430","Otros"};
                for(i=0;i<Ferrari.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[11]);
                	initialValues.put(KEY_modelo, Ferrari[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                

                String[] Fiat={"500","Albea","Bravo","Ducato","Grande Punto","Idea Adventure","Palio","Palio Adventure","Panda","Stilo","Strada","Otros"};
                for(i=0;i<Fiat.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[12]);
                	initialValues.put(KEY_modelo, Fiat[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                
                String[] Ford={"Aerostar","Bronco","Club Wagon","Contour","Cougar","Courier","Crown Victoria","E-150","E-350","Econoline","EcoSport","Edge","Escape","Escort","Excursion","Expediton","Explorer","F-150","F-250","F-350","F-450","F-550","Fiesta","Otros"};
                for(i=0;i<Ford.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[13]);
                	initialValues.put(KEY_modelo, Ford[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                
                String[] GMC={"Acadia","AstroVan","Canyon","Jimmy","Savana", "Sierra","Pick-Up","Terrain","Yukon","Otros"};
                for(i=0;i<GMC.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[14]);
                	initialValues.put(KEY_modelo, GMC[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                

                String[] Honda={"Accord","City","Civic","Crosstour","CR-V","CRX","CR-Z","Element","Fit","Odyssey","Passport","Pilot","Ridgeline","Otros"};
                for(i=0;i<Honda.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[15]);
                	initialValues.put(KEY_modelo, Honda[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Hummer={"H1","H2","H3","Otros"};
                for(i=0;i<Hummer.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[16]);
                	initialValues.put(KEY_modelo, Hummer[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                String[] Hyundai={"Atos","i10","Otros"};
                for(i=0;i<Hyundai.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[17]);
                	initialValues.put(KEY_modelo, Hyundai[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Infiniti={"Fx 35","G37","i30","Q45","Otros"};
                for(i=0;i<Infiniti.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[18]);
                	initialValues.put(KEY_modelo, Infiniti[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }               
                
                String[] Isuzu={"Amigo","Rodeo","Otros"};
                for(i=0;i<Isuzu.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[19]);
                	initialValues.put(KEY_modelo, Isuzu[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }      
                
                String[] Jaguar={"S-Type","X-Type","XF","XJ","XKR","Otros"};
                for(i=0;i<Jaguar.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[20]);
                	initialValues.put(KEY_modelo, Jaguar[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Jeep={"Cherokee","Cherokee Sport","CJ5","CJ7","Comanche","Commander","Compass","Grand Cherokee","Grand Wagoneer","Liberty","Patriot","Renegado","Rubicon","Wagoneer","Willys","Wrangler","Otros"};
                for(i=0;i<Jeep.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[21]);
                	initialValues.put(KEY_modelo, Jeep[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                
                String[] Lamborghini={"Gallardo","Otros"};
                for(i=0;i<Lamborghini.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[22]);
                	initialValues.put(KEY_modelo, Lamborghini[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
            

                String[] LandRover={"Defender","Discovery","Freelander","LR2","L","","LR4","Range Rover","Otros"};
                for(i=0;i<LandRover.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[23]);
                	initialValues.put(KEY_modelo, LandRover[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
     
                String[] Lincoln={"Aviator","Black Wood","Continental","LS","Mark LT","MKS","MKX","MKZ","Navigator","Town Car","Zephyr","Otros"};
                for(i=0;i<Lincoln.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[24]);
                	initialValues.put(KEY_modelo, Lincoln[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

     
                String[] MG={"TF","Otros"};
                for(i=0;i<MG.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[25]);
                	initialValues.put(KEY_modelo, MG[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                

                String[] Mazda={"CX-7","CX-9","Mazda 2","Mazda 3","Mazda 5","Mazda 6","Mazda Speed 3","MX-5","MX-6","Pick-Up","Protege","Otros"};
                for(i=0;i<Mazda.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[26]);
                	initialValues.put(KEY_modelo, Mazda[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                

                String[] MercedesBenz={"Clase A","Clase B","Clase C","Clase CL","Clase CLA","Clase CLS","Clase E","Clase GL","Clase GLK","Clase M","Clase R","Clase S","Clase SLK","Clase SLR","CLK","ML","Smart","Sprinter","Vito","Otros"};
                for(i=0;i<MercedesBenz.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[27]);
                	initialValues.put(KEY_modelo, MercedesBenz[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Mercury={"Mariner","Montainer","Montego","Mystique","Villager","Otros"};
                for(i=0;i<Mercury.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[28]);
                	initialValues.put(KEY_modelo, Mercury[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                String[] Mini={"Clubman","Cooper","Countryman","Jhon Cooper Works","Otros"};
                for(i=0;i<Mini.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[29]);
                	initialValues.put(KEY_modelo, Mini[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                
                String[] Mitsubishi={"Eclipse","Endeavor","Galant","Grandis","L200","Lancer","Mirage","Montero","Outlander","Otros"};
                for(i=0;i<Mitsubishi.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[30]);
                	initialValues.put(KEY_modelo, Mitsubishi[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Nissan={"240 SX","300 ZX","350 Z","370Z","Almera","Altima","Aprio","Armada","Cabstar","Doble Cabina","Frontier","Hikari","Ichi Van","Infiniti","Juke","Lucino","March","Maxima","Micra","Murano","Note","NP300","Pathfinder","PickUp","PickUpEstaquitas","Platina","Quest","Rogue","SE-R","Sentra","Tiida","Titan","TSubame","Tsuru","Tsuru2","Urvan","Versa","X-Terra","X-Trail","Yuke","Otros"};
                for(i=0;i<Nissan.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[31]);
                	initialValues.put(KEY_modelo, Nissan[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                                
                String[] Peugeot={"206","207","306","307","308","405","406","407","607","3008","307 SW","Grand Raid","Manager","Partner","RCZ","Otros"};
                for(i=0;i<Peugeot.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[32]);
                	initialValues.put(KEY_modelo, Peugeot[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Pontiac={"Aztek","Boneville","Fiero","Firebird","G3","G4","G5","G6","Grand Am","Grand Prix","Matiz","Montana","Solstice","Sunfire","Torrent","Trans Am","Trans Sport","Otros"};
                for(i=0;i<Pontiac.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[33]);
                	initialValues.put(KEY_modelo, Pontiac[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                String[] Porsche={"911","Boxster","Carrera","Cayenne","Cayman","Panamera","Speedster","Otros"};
                for(i=0;i<Porsche.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[34]);
                	initialValues.put(KEY_modelo, Porsche[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                String[] Renault={"Alliance","Clio","Duster","Fluence","Kangoo","Kangoo Express","Koleos","Laguna","Megane","Safrane","Sandero","Scala","Scanic","Stepway","Trafic","Otros"};
                for(i=0;i<Renault.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[35]);
                	initialValues.put(KEY_modelo, Renault[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                
                String[] Saab={"9-3","9-5","Otros"};
                for(i=0;i<Saab.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[36]);
                	initialValues.put(KEY_modelo, Saab[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                

                String[] Seat={"Alhambra","Altea","Bocanegra","Cordoba","Exeo","Freetrack","Ibiza","Leon","Toledo","Otros"};
                for(i=0;i<Seat.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[37]);
                	initialValues.put(KEY_modelo, Seat[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }

                
                String[] Smart={"Forfour","Fortwo","Otros"};
                for(i=0;i<Smart.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[38]);
                	initialValues.put(KEY_modelo, Smart[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }
                
                String[] Subaru={"B9 tribeca","Forester","Impreza","Legacy","Outback","Otros"};
                for(i=0;i<Subaru.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[39]);
                	initialValues.put(KEY_modelo, Subaru[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }         

                String[] Suzuki={"Aerio","Grand Vitara","Kizashi","Samurai","Swift","SX4","XL7","Otros"};
                for(i=0;i<Suzuki.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[40]);
                	initialValues.put(KEY_modelo, Suzuki[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }         
                
                String[] Toyota={"4Runner","Avanza","Camry","Celica","Corolla","Corona","Echo","FJ Cruiser","Hiace","Highlander","Hilux","Matrix","MR2","Pick-Up","Prius","RAV4","Rush","Sequoia","Sienna","Solara","T-100","Tacoma","Tundra","Otros"};
                for(i=0;i<Toyota.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[41]);
                	initialValues.put(KEY_modelo, Toyota[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }         

                String[] VolksWagen={"Amarok","Atlantic","Beetle","Bora","Bora Sportwagen","Cabriolet","Caribe","Clasico","Combi","Corsar","Crafter","Crossfox","Derby","Eos","Eurovan","GLI","Gol","Golf","GTI","Jetta","Lupo","New Beetle","Passat","Otros"};
                for(i=0;i<VolksWagen.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[42]);
                	initialValues.put(KEY_modelo, VolksWagen[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }    
                
                String[] Volvo={"C30","C70","S40","S60","S70","S80","S90","V40","V50","XC60","XC70","XC90","Otros"};
                for(i=0;i<Volvo.length;i++)
                {
                	initialValues.put(KEY_marca, marcas[43]);
                	initialValues.put(KEY_modelo, Volvo[i]);
                	db.insert(DATABASE_TABLE_Modelos, null, initialValues);
                }         

                //agregando opcion Otros
            	initialValues.put(KEY_marca, marcas[44]);
            	initialValues.put(KEY_modelo, "Otros");
            	db.insert(DATABASE_TABLE_Modelos, null, initialValues);

                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS vehiculos_modelos");
            db.execSQL("DROP TABLE IF EXISTS vehiculos_marcas");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        DBHelper.close();
    }
    
    
    
    //---retrieves all the contacts---
    public Cursor getAllMarcas()
    {
        return db.query(DATABASE_TABLE_Marcas, new String[] {KEY_id, KEY_marca}, null, null, null, null, null);
    }
    
    public Cursor getAllModel(String marca)
    {
    	
        return db.query(DATABASE_TABLE_Modelos, new String[] {KEY_id, KEY_modelo}, KEY_marca + "='"+marca.toString()+"'", null, null, null, null);
    }
    
    public Cursor getAño(int id_model)
    {
        return db.query(DATABASE_TABLE_Modelos, new String[] {KEY_id, KEY_año_i,KEY_año_f},  KEY_id + "=" + id_model, null, null, null, null);
    }

    
    

/*
    //---insert a contact into the database---
    public long insertContact(String name, String email) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteContact(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
     
    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    
    //---updates a contact---
    public boolean updateContact(long rowId, String name, String email) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
*/
}
