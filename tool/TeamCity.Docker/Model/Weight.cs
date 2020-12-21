namespace TeamCity.Docker.Model
{
    internal readonly struct Weight
    {
        public static readonly Weight Empty = new Weight(0);

        public readonly int Value;

        public Weight(int value) => Value = value;

        public override string ToString() => Value.ToString();
    }
}
