//
// Translated by CS2J (http://www.cs2j.com): 10/5/2015 1:33:49 PM
//

package Mls;

//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:2.0.50727.1433
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------
/**
* A strongly-typed resource class, for looking up localized strings, etc.
*/
// This class was auto-generated by the StronglyTypedResourceBuilder
// class via a tool like ResGen or Visual Studio.
// To add or remove a member, edit your .ResX file then rerun ResGen
// with the /str option, or rebuild your VS project.
public class SearchResource   
{
    private static System.Resources.ResourceManager resourceMan = new System.Resources.ResourceManager();
    private static System.Globalization.CultureInfo resourceCulture = new System.Globalization.CultureInfo();
    public SearchResource() throws Exception {
    }

    /**
    * Returns the cached ResourceManager instance used by this class.
    */
    public static System.Resources.ResourceManager getResourceManager() throws Exception {
        if (Object.ReferenceEquals(resourceMan, null))
        {
            System.Resources.ResourceManager temp = new System.Resources.ResourceManager("SearchEngine.SearchResource", SearchResource.class.Assembly);
            resourceMan = temp;
        }
         
        return resourceMan;
    }

    /**
    * Overrides the current thread's CurrentUICulture property for all
    * resource lookups using this strongly typed resource class.
    */
    public static System.Globalization.CultureInfo getCulture() throws Exception {
        return resourceCulture;
    }

    public static void setCulture(System.Globalization.CultureInfo value) throws Exception {
        resourceCulture = value;
    }

    /**
    * Looks up a localized string similar to <TCResult ReplyCode="0" ReplyText="Success">
    * <Listings>
    * <Listing PropertyType_STDF="Single-family" PropertyType_MLS="Residential" DEFType="Residential" MLSNumber="40341361" Status_STDF="A" Status_MLS="Active" HouseNo="2129" StreetName="19th Ave" SuiteNo="" City="Oakland" State="CA" Zip="94606" NumberUnits="" BedRooms="2" TotalBathRooms_MLS="1 / 0" TotalBathRooms_STDF="1" FullBathRooms="1" HalfBathRooms="0" QuarterBathRooms="" LotSize_MLS="3090" LotSize_STDF="0.071" SquareFeet_MLS="960" SquareFeet_STDF= [rest of string was truncated]";.
    */
    public static String getmlsPlusStandardResult() throws Exception {
        return getResourceManager().GetString("mlsPlusStandardResult", resourceCulture);
    }

}


