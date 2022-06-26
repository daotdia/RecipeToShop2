import SwiftUI
import sharedApp

@main
struct iOSApp: App {
    
    private let cacheModule: CacheModule
    private let caseUses: UseCases
    
    //Inicializo la base de datos y los casos de uso que se utilizarán en toda la APP.
    init(){
        self.cacheModule = CacheModule()
        self.caseUses = UseCases(cacheModule: self.cacheModule)
    }
    
    //Las flags necesarias para gestionar diaolgos e iconos dinámicos
    @State var openDeleteIcon: Bool = false
    @State var openDialogDelete: Bool = false
    
	var body: some Scene {
		WindowGroup {
            //Es aquí donde está toda la lógica de navegación y los menus de toda la aplicación.
            NavigationView{
                //La navegación se realiza mediante menú inferior.
                TabView{
                    Despensa(
                        caseUses: caseUses,
                        openDeleteIcon: $openDeleteIcon,
                        openDialogDelete: $openDialogDelete
                    )
                        .tabItem(){
                            //TODO: Poner las imagenes correctas de los iconos de los menus.
                            Image(systemName: "heart.fill")
                            Text("Despensa")
                        }
                    //TODO: Crear Screen de recetas
                    Text("Recetas")
                        .tabItem(){
                            //TODO: Poner las imagenes correctas de los iconos de los menus.
                            Image(systemName: "heart.fill")
                            Text("Recetas")
                        }
                    //TODO: Crear screen de compra.
                    Text("Compra")
                        .tabItem(){
                            //TODO: Poner las imagenes correctas de los iconos de los menus.
                            Image(systemName: "heart.fill")
                            Text("Compra")
                        }                }
            }
            .onTapGesture(count: 2, perform: {
                //Aquí reinicio todas las flags que deben de reiniciarse en el caso de que el usuario cambie de foco.
                openDeleteIcon = false
                openDialogDelete = false
            })
        }
    }
}
